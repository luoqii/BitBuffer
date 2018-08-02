package org.bbs.nio;

import java.nio.ByteOrder;

public class Bits {

    public static int getBits(byte value, int startBit, int stopBit, ByteOrder order){
        if (!(0 <= startBit && startBit <= stopBit  && startBit <=7)){
            throw new IllegalArgumentException("invalid arg. startBit:" + startBit + " stopBit:" + stopBit);
        }

        int bitValue = 0;
        int mask = 0;
        if (order == ByteOrder.BIG_ENDIAN){
            mask = mask(startBit, stopBit, order);
            bitValue = value & mask;
            int shift = 8 - stopBit - 1;
            bitValue = bitValue >> shift;
        } else {
            mask = mask(startBit, stopBit, order);
            bitValue = value & mask;
            bitValue = bitValue >> (startBit);
        }

        return bitValue;
    }

    public static int mask(int startBit, int stopBit, ByteOrder order){
        int mask = 0;

        if (order == ByteOrder.BIG_ENDIAN){
            for (int i = startBit ; startBit != stopBit && i <= stopBit ; i++){
                int shift = 8 - i - 1;
                mask |= (1 << shift);
            }
        } else {
            for (int i = startBit ; i < stopBit ; i++){
                mask |= (1 << i);
            }
        }

        return mask;
    }

}
