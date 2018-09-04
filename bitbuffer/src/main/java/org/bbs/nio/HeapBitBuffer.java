package org.bbs.nio;


import android.util.Log;

import java.nio.ByteOrder;
import java.util.Arrays;

public class HeapBitBuffer extends BitBuffer {
    private static final boolean DEBUG = false;
    private static final String TAG = HeapBitBuffer.class.getSimpleName();

    private final byte[] mData;

    public HeapBitBuffer(int bitSize) {
        super(-1, 0, bitSize, bitSize);

        int byteSize = (bitSize + 7 ) / 8;
        mData = new byte[byteSize];
    }

    public HeapBitBuffer(byte[] array){
        super(-1, 0, array.length * 8, array.length * 8);

        mData = array;
    }

    @Override
    public boolean hasArray() {
        return true;
    }

    @Override
    public byte[] array() {
        return mData;
    }

    @Override
    public int getInt(int bitSize){
        if (position + bitSize > limit()){
            throw new BufferUnderflowException("positon:" + position + " bitSize:" + bitSize + " limit:" + limit());
        }
        int value = getIntAt(position, bitSize);
        position += bitSize;
        return value;
    }


    @Override
    public int getInt(int index, int bitSize) {
        if (position + bitSize > limit()){
            throw new IndexOutOfBoundsException("positon:" + position + " bitSize:" + bitSize + " limit:" + limit());
        }
        return getIntAt(index, bitSize);
    }

    int getIntAt(int index, int bitSize) {
        if (DEBUG){
            Bits.log(TAG, "getInt index:" + index + " bitSize:" + bitSize + " position:" + position);
        }
        checkBitSize(32, bitSize);

        int value = 0;

        ByteOrder order = order();
        int stopBitPosition = index + bitSize;
        int bytePosition4Read = index / 8;
        int bitPositionInCurrentByte = index % 8;
        int startBit;
        int stopBit;
        int shift;
        int readBitSize;
        int cumulativeReadBitSize = 0;

        for (int i = index ; i < stopBitPosition;){
            int nextBytePosition = (index + 8) / 8 * 8;
            int bitValue;
            if (stopBitPosition > nextBytePosition) { // 跨Byte
                readBitSize = 8 - bitPositionInCurrentByte;
                startBit = bitPositionInCurrentByte;
                stopBit = bitPositionInCurrentByte + readBitSize;

                bitValue = Bits.getByteBits(mData[bytePosition4Read], startBit, stopBit, order);
                bitValue &= 0xFF;
                cumulativeReadBitSize += readBitSize;

                if (ByteOrder.BIG_ENDIAN == order){
                    shift = bitSize - cumulativeReadBitSize;
                } else {
                    shift = cumulativeReadBitSize - readBitSize;
                }
                if (DEBUG){
                    //++++++++++++++++++++++1234567890123456789012345678901234567890
                    Bits.log(TAG, "getInt before add.    shift:" + shift);
                }
                bitValue = (bitValue << shift);

                if (DEBUG){
                    //++++++++++++++++++++++1234567890123456789012345678901234567890
                    Bits.log(TAG, "getInt before add. bitValue:" + bitValue + " " + Bits.intStr4Debug(bitValue));
                }
                value |= (bitValue);
                if (DEBUG){
                    //++++++++++++++++++++++1234567890123456789012345678901234567890
                    Bits.log(TAG, "getInt  after add.    value:" + value + " " + Bits.intStr4Debug(value));
                }

                bitPositionInCurrentByte = 0;
                index += readBitSize;
                bytePosition4Read++;
                i += readBitSize;
            } else {
                readBitSize = bitSize - cumulativeReadBitSize;
                startBit = bitPositionInCurrentByte;
                stopBit = bitPositionInCurrentByte + readBitSize;

                bitValue = Bits.getByteBits(mData[bytePosition4Read], startBit, stopBit, order);
                bitValue &= 0xFF;
                cumulativeReadBitSize += readBitSize;

                if (ByteOrder.LITTLE_ENDIAN == order){
                    shift = cumulativeReadBitSize - readBitSize;
                    if (DEBUG){
                        //++++++++++++++++++++++1234567890123456789012345678901234567890
                        Bits.log(TAG, "getInt before add.    shift:" + shift);
                    }
                    bitValue = bitValue << shift;
                }
                if (DEBUG){
                    //++++++++++++++++++++++1234567890123456789012345678901234567890
                    Bits.log(TAG, "getInt before add. bitValue:" + bitValue + " " + Bits.intStr4Debug(bitValue));
                }
                value |= bitValue;
                if (DEBUG){
                    //++++++++++++++++++++++1234567890123456789012345678901234567890
                    Bits.log(TAG, "getInt  after add.    value:" + value + " " + Bits.intStr4Debug(value));
                }

                index += readBitSize;
                i += readBitSize;
            }
        }

        if (DEBUG){
            Bits.log(TAG, "getInt. value:" + value + " " + Bits.intStr4Debug(value));
        }
        return value;
    }


    @Override
    public BitBuffer putInt(int bitSize, int value) {
        if (position + bitSize > limit()){
            throw new BufferOverflowException("positon:" + position + " bitSize:" + bitSize + " limit:" + limit());
        }
        putIntAt(position, bitSize, value);
        position += bitSize;
        return this;
    }

    @Override
    public BitBuffer putInt(int index, int bitSize, int value) {
        if (position + bitSize > limit()){
            throw new IndexOutOfBoundsException("positon:" + position + " bitSize:" + bitSize + " limit:" + limit());
        }
        return putIntAt(index, bitSize, value);
    }

    BitBuffer putIntAt(int index, int bitSize, int value) {
        if (DEBUG){
            Bits.log(TAG, "setInt. bitSize:" + bitSize + " value:" + value + " " + Bits.intStr4Debug(value));
            Bits.log(TAG, "setInt.   index:" + index);
        }
        checkBitSize(32, bitSize);

        ByteOrder order = order();
        int currentBytePosition = index / 8; // in byte
        int stopBitPosition = index + bitSize;
        int bitPositionInCurrentByte = index % 8; // begin 0
        int startBit4Write = 0;
        int stopBit4Write = 0;
        int startBit4Read = order == ByteOrder.LITTLE_ENDIAN ? 0 : 32 - bitSize;
        int stopBit4Read = 0;
        int currentValue = 0;

        //startBit4Read = 0;
        for (; index < stopBitPosition;){
            int nextBytePosition = (index + 8) / 8 * 8;

            startBit4Write = bitPositionInCurrentByte;
            if (stopBitPosition > nextBytePosition) {// 跨byte
                stopBit4Write = 8;

                int bit2Write = stopBit4Write - startBit4Write;
                stopBit4Read = startBit4Read + bit2Write;
                currentValue = Bits.getIntBits(value, startBit4Read, stopBit4Read, order);

                if (DEBUG){
                    Bits.log(TAG, "setInt. set currentBytePosition:" + currentBytePosition + " currentValue:" + currentValue + " " + Bits.intStr4Debug(currentValue));
                    Bits.log(TAG, "setInt. set startBit4Write:" + startBit4Write + " stopBit4Write:" + stopBit4Write + " order:" + order);
                }
                Bits.set(mData, currentBytePosition, currentValue, startBit4Write, stopBit4Write, order);

                startBit4Read += bit2Write;
                currentBytePosition++;
                index = nextBytePosition;
                bitPositionInCurrentByte = 0;
                bitSize -= bit2Write;
            } else { // 单个byte内
                stopBit4Read = startBit4Read + bitSize;
                currentValue = Bits.getIntBits(value, startBit4Read, stopBit4Read, order);

                stopBit4Write = stopBitPosition % 8;
                if (stopBit4Write == 0){
                    stopBit4Write = 8;
                }
                Bits.set(mData, currentBytePosition, currentValue, startBit4Write, stopBit4Write, order);

                index = stopBitPosition;
            }
        }

        return this;
    }

    @Override
    public BitBuffer put(byte[] values) {
        return put(values, 0, values.length);
    }

    @Override
    public BitBuffer put(byte[] values, int offset, int length) {
        byte[] values2put = Arrays.copyOfRange(values, offset, offset + length);
        for (byte b : values2put) {
            putInt(8, b);
        }
        return this;
    }

    @Override
    public BitBuffer get(byte[] values, int offset, int length) {
        for (int i = 0 ; i < length ; i++){
            values[offset + i] = (byte) getInt(8);
        }
        return this;
    }

    @Override
    public BitBuffer get(byte[] values) {
        return get(values, 0, values.length);
    }

    void checkBitSize(int maxSize, int bitSize){
        if (bitSize <= 0 || bitSize > maxSize){
            throw new IllegalArgumentException("invalid bitSize:" + bitSize + " maxSize:" + maxSize);
        }
    }
}
