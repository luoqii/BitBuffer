package org.bbs.nio;

/**
 * A typesafe enumeration for bit orders.
 */

public final class BitOrder {

    private String name;

    private BitOrder(String name) {
        this.name = name;
    }

    /**
     * Constant denoting big-endian byte order.  In this order, the bytes of a
     * multibyte value are ordered from most significant to least significant.
     */
    public static final BitOrder BIG_ENDIAN
            = new BitOrder("BIG_ENDIAN");

    /**
     * Constant denoting little-endian byte order.  In this order, the bytes of
     * a multibyte value are ordered from least significant to most.
     */
    public static final BitOrder LITTLE_ENDIAN
            = new BitOrder("LITTLE_ENDIAN");


    /**
     * Constructs a string describing this object.
     *
     * <p> This method returns the string <tt>"BIG_ENDIAN"</tt> for {@link
     * #BIG_ENDIAN} and <tt>"LITTLE_ENDIAN"</tt> for {@link #LITTLE_ENDIAN}.
     * </p>
     *
     * @return The specified string
     */
    public String toString() {
        return name;
    }

}