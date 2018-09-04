package org.bbs.nio;


import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteOrder;
import java.nio.InvalidMarkException;
import java.nio.ReadOnlyBufferException;

//http://androidxref.com/8.1.0_r33/xref/libcore/ojluni/src/main/java/java/nio/Buffer.java
/*
 * java 中所以数字类型都是按照 BIG_ENDIAN 表示的
 * ByteBuffer 可以支持 BIG_ENDIAN，LITTLE_ENDIAN 2中字节序，但不支持bit序
 * 与字节序类似地，我们提供bit序
 * 我们假定：
 *  #bit序与字节序相同
 */
abstract public class BitBuffer {

    // Invariants: mark <= position <= limit <= capacity
    private int mark = -1;
    int position = 0;
    private int limit;
    private int capacity;

    // Creates a new buffer with the given mark, position, limit, and capacity,
    // after checking invariants.
    //
    protected BitBuffer(int mark, int pos, int lim, int cap) {       // package-private
        if (cap < 0)
            throw new IllegalArgumentException("Negative capacity: " + cap);
        this.capacity = cap;
        limit(lim);
        position(pos);
        if (mark >= 0) {
            if (mark > pos)
                throw new IllegalArgumentException("mark > position: ("
                        + mark + " > " + pos + ")");
            this.mark = mark;
        }
    }

    /**
     * Returns this buffer's capacity.
     *
     * @return The capacity of this buffer
     */
    public final int capacity() {
        return capacity;
    }

    /**
     * Returns this buffer's position.
     *
     * @return The position of this buffer
     */
    public final int position() {
        return position;
    }

    /**
     * Sets this buffer's position.  If the mark is defined and larger than the
     * new position then it is discarded.
     *
     * @param newPosition The new position value; must be non-negative
     *                    and no larger than the current limit
     * @return This buffer
     * @throws IllegalArgumentException If the preconditions on <tt>newPosition</tt> do not hold
     */
    public final BitBuffer position(int newPosition) {
        if ((newPosition > limit) || (newPosition < 0))
            throw new IllegalArgumentException("Bad position " + newPosition + "/" + limit);
        position = newPosition;
        if (mark > position) mark = -1;
        return this;
    }

    /**
     * Returns this buffer's limit.
     *
     * @return The limit of this buffer
     */
    public final int limit() {
        return limit;
    }

    /**
     * Sets this buffer's limit.  If the position is larger than the new limit
     * then it is set to the new limit.  If the mark is defined and larger than
     * the new limit then it is discarded.
     *
     * @param newLimit The new limit value; must be non-negative
     *                 and no larger than this buffer's capacity
     * @return This buffer
     * @throws IllegalArgumentException If the preconditions on <tt>newLimit</tt> do not hold
     */
    public final BitBuffer limit(int newLimit) {
        if ((newLimit > capacity) || (newLimit < 0))
            throw new IllegalArgumentException();
        limit = newLimit;
        if (position > limit) position = limit;
        if (mark > limit) mark = -1;
        return this;
    }

    /**
     * Sets this buffer's mark at its position.
     *
     * @return This buffer
     */
    public final BitBuffer mark() {
        mark = position;
        return this;
    }

    /**
     * Resets this buffer's position to the previously-marked position.
     *
     * <p> Invoking this method neither changes nor discards the mark's
     * value. </p>
     *
     * @return This buffer
     * @throws InvalidMarkException If the mark has not been set
     */
    public final BitBuffer reset() {
        int m = mark;
        if (m < 0)
            throw new InvalidMarkException();
        position = m;
        return this;
    }

    /**
     * Clears this buffer.  The position is set to zero, the limit is set to
     * the capacity, and the mark is discarded.
     *
     * <p> Invoke this method before using a sequence of channel-read or
     * <i>put</i> operations to fill this buffer.  For example:
     *
     * <blockquote><pre>
     * buf.clear();     // Prepare buffer for reading
     * in.read(buf);    // Read data</pre></blockquote>
     *
     * <p> This method does not actually erase the data in the buffer, but it
     * is named as if it did because it will most often be used in situations
     * in which that might as well be the case. </p>
     *
     * @return This buffer
     */
    public final BitBuffer clear() {
        position = 0;
        limit = capacity;
        mark = -1;
        return this;
    }

    /**
     * Flips this buffer.  The limit is set to the current position and then
     * the position is set to zero.  If the mark is defined then it is
     * discarded.
     *
     * <p> After a sequence of channel-read or <i>put</i> operations, invoke
     * this method to prepare for a sequence of channel-write or relative
     * <i>get</i> operations.  For example:
     *
     * <blockquote><pre>
     * buf.put(magic);    // Prepend header
     * in.read(buf);      // Read data into rest of buffer
     * buf.flip();        // Flip buffer
     * out.write(buf);    // Write header + data to channel</pre></blockquote>
     *
     * <p> This method is often used in conjunction with the {@link
     * java.nio.ByteBuffer#compact compact} method when transferring data from
     * one place to another.  </p>
     *
     * @return This buffer
     */
    public final BitBuffer flip() {
        limit = position;
        position = 0;
        mark = -1;
        return this;
    }

    /**
     * Rewinds this buffer.  The position is set to zero and the mark is
     * discarded.
     *
     * <p> Invoke this method before a sequence of channel-write or <i>get</i>
     * operations, assuming that the limit has already been set
     * appropriately.  For example:
     *
     * <blockquote><pre>
     * out.write(buf);    // Write remaining data
     * buf.rewind();      // Rewind buffer
     * buf.get(array);    // Copy data into array</pre></blockquote>
     *
     * @return This buffer
     */
    public final BitBuffer rewind() {
        position = 0;
        mark = -1;
        return this;
    }

    /**
     * Returns the number of elements between the current position and the
     * limit.
     *
     * @return The number of elements remaining in this buffer
     */
    public final int remaining() {
        return limit - position;
    }

    /**
     * Tells whether there are any elements between the current position and
     * the limit.
     *
     * @return <tt>true</tt> if, and only if, there is at least one element
     * remaining in this buffer
     */
    public final boolean hasRemaining() {
        return position < limit;
    }

    // -- Package-private methods for bounds checking, etc. --

    /**
     * Checks the current position against the limit, throwing a {@link
     * BufferUnderflowException} if it is not smaller than the limit, and then
     * increments the position.
     *
     * @return The current position value, before it is incremented
     */
    final int nextGetIndex() {                          // package-private
        if (position >= limit)
            throw new BufferUnderflowException();
        return position++;
    }

    final int nextGetIndex(int nb) {                    // package-private
        if (limit - position < nb)
            throw new BufferUnderflowException();
        int p = position;
        position += nb;
        return p;
    }

    /**
     * Checks the current position against the limit, throwing a {@link
     * BufferOverflowException} if it is not smaller than the limit, and then
     * increments the position.
     *
     * @return The current position value, before it is incremented
     */
    final int nextPutIndex() {                          // package-private
        if (position >= limit)
            throw new BufferOverflowException();
        return position++;
    }

    final int nextPutIndex(int nb) {                    // package-private
        if (limit - position < nb)
            throw new BufferOverflowException();
        int p = position;
        position += nb;
        return p;
    }

    /**
     * Checks the given index against the limit, throwing an {@link
     * IndexOutOfBoundsException} if it is not smaller than the limit
     * or is smaller than zero.
     */
    final int checkIndex(int i) {                       // package-private
        if ((i < 0) || (i >= limit))
            // Android-changed: Add bounds details to exception.
            throw new IndexOutOfBoundsException(
                    "index=" + i + " out of bounds (limit=" + limit + ")");
        return i;
    }

    final int checkIndex(int i, int nb) {               // package-private
        if ((i < 0) || (nb > limit - i))
            // Android-changed: Add bounds details to exception.
            throw new IndexOutOfBoundsException(
                    "index=" + i + " out of bounds (limit=" + limit + ", nb=" + nb + ")");
        return i;
    }

    final int markValue() {                             // package-private
        return mark;
    }

    final void truncate() {                             // package-private
        mark = -1;
        position = 0;
        limit = 0;
        capacity = 0;
    }

    final void discardMark() {                          // package-private
        mark = -1;
    }

    static void checkBounds(int off, int len, int size) { // package-private
        if ((off | len | (off + len) | (size - (off + len))) < 0)
            // Android-changed: Add bounds details to exception.
            throw new IndexOutOfBoundsException(
                    "off=" + off + ", len=" + len + " out of bounds (size=" + size + ")");
    }

    boolean bigEndian                                   // package-private
            = true;
//    boolean nativeByteOrder                             // package-private
//            = (Bits.byteOrder() == ByteOrder.BIG_ENDIAN);

    /**
     * Retrieves this buffer's byte order.
     *
     * <p> The byte order is used when reading or writing multibyte values, and
     * when creating buffers that are views of this byte buffer.  The order of
     * a newly-created byte buffer is always {@link ByteOrder#BIG_ENDIAN
     * BIG_ENDIAN}.  </p>
     *
     * @return This buffer's byte order
     */
    public final ByteOrder order() {
        return bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
    }

    /**
     * Modifies this buffer's byte order.  </p>
     *
     * @param bo The new byte order,
     *           either {@link ByteOrder#BIG_ENDIAN BIG_ENDIAN}
     *           or {@link ByteOrder#LITTLE_ENDIAN LITTLE_ENDIAN}
     * @return This buffer
     */
    public final BitBuffer order(ByteOrder bo) {
        if (position % 8 != 0){
            throw new IllegalStateException("current position[" + position + "] is not at byte boundary, ie 8, 16, 32 ...");
        }
        bigEndian = (bo == ByteOrder.BIG_ENDIAN);
//        nativeByteOrder =
//                (bigEndian == (Bits.byteOrder() == ByteOrder.BIG_ENDIAN));
        return this;
    }

    /**
     * Tells whether or not this buffer is backed by an accessible
     * array.
     *
     * <p> If this method returns <tt>true</tt> then the {@link #array() array}
     * and {@link #arrayOffset() arrayOffset} methods may safely be invoked.
     * </p>
     *
     * @return <tt>true</tt> if, and only if, this buffer
     * is backed by an array and is not read-only
     * @since 1.6
     */
    public abstract boolean hasArray();

    /**
     * Returns the array that backs this
     * buffer&nbsp;&nbsp;<i>(optional operation)</i>.
     *
     * <p> This method is intended to allow array-backed buffers to be
     * passed to native code more efficiently. Concrete subclasses
     * provide more strongly-typed return values for this method.
     *
     * <p> Modifications to this buffer's content will cause the returned
     * array's content to be modified, and vice versa.
     *
     * <p> Invoke the {@link #hasArray hasArray} method before invoking this
     * method in order to ensure that this buffer has an accessible backing
     * array.  </p>
     *
     * @return The array that backs this buffer
     * @throws ReadOnlyBufferException       If this buffer is backed by an array but is read-only
     * @throws UnsupportedOperationException If this buffer is not backed by an accessible array
     * @since 1.6
     */
    public abstract byte[] array();


    // interface

    public static BitBuffer allocateInByte(int byteSize){
        return allocate(byteSize * 8);
    }

    public static BitBuffer allocate(int bitSize){
        if (bitSize < 0)
            throw new IllegalArgumentException();
        return new HeapBitBuffer(bitSize);
    }

    public static BitBuffer wrap(byte[] array){
        return new HeapBitBuffer(array);
    }

    /**
     * Relative get method for reading a int value.
     * @param bitSize
     * @return
     */
    public abstract int getInt(int bitSize);

    /**
     * Absolute get method for reading a int value.
     * @param index
     * @param bitSize
     * @return
     */
    public abstract int getInt(int index, int bitSize);

    /**
     * Relative put method for writing an int value
     * @param bitSize
     * @param value
     * @return
     */
    public abstract BitBuffer putInt(int bitSize, int value);

    /**
     * Absolute put method for writing an int value
     * @param index
     * @param bitSize
     * @param value
     * @return
     */
    public abstract BitBuffer putInt(int index, int bitSize, int value);

    /**
     * Relative put method for writing byte array from this Bitbuffer
     * @param values
     * @return
     */
    public abstract BitBuffer put(byte[] values);

    /**
     * Relative put method for writing byte array from this Bitbuffer
     * @param values
     * @param  offset
     * @param length
     * @return
     */
    public abstract BitBuffer put(byte[] values, int offset, int length);

    /**
     * Relative get method for read byte to this Bitbuffer
     * @param values
     * @param  offset
     * @param length
     * @return
     */
    public abstract BitBuffer get(byte[] values, int offset, int length);

    /**
     * Relative get method for read byte too this Bitbuffer
     * @param values
     * @return
     */
    public abstract BitBuffer get(byte[] values);
}
