package org.bbs.nio;


class HeapBitBuffer extends BitBuffer {
    private final byte[] mData;

    public HeapBitBuffer(int bitSize) {
        super(-1, 0, bitSize, bitSize);

        int byteSize = (bitSize + 7 ) / 8 * 8;
        mData = new byte[byteSize];
    }

    @Override
    public int getInt(int bitSize) {
        return 0;
    }

    @Override
    public BitBuffer setInt(int bitSize, byte value) {
        return null;
    }
}
