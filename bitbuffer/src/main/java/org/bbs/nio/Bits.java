package org.bbs.nio;

import android.support.annotation.VisibleForTesting;

import java.nio.ByteOrder;

import static android.support.annotation.VisibleForTesting.PRIVATE;

public class Bits {
    private static final boolean DEBUG = true;
    private static final String TAG = "Bits";

    /**
     * 从 value 中，按照开始startBit位置到结束stopBit位置 取值
     * 0 <= startBit < stopBit <= 32
     * @param value 待取值，默认 BIG_ENDIAN 存放
     * @param startBit 开始bit 位置（包括）
     * @param stopBit  停止bit 位置（不包括）
     * @param order 将 value 按指定字节序读取
     * @return
     */
    public static int getIntBits(int value, int startBit, int stopBit, ByteOrder order){
        return getBits(value, 4, startBit, stopBit, order);
    }

    /**
     * 从 value 中，按照开始startBit位置到结束stopBit位置 取值
     * 0 <= startBit < stopBit <= 8
     * @param value 待取值，默认 BIG_ENDIAN 存放
     * @param startBit 开始bit 位置（包括）
     * @param stopBit  停止bit 位置（不包括）
     * @param order 将 value 按指定字节序读取
     * @return
     */
    public static int getByteBits(byte value, int startBit, int stopBit, ByteOrder order){
        return getBits(value, 1, startBit, stopBit, order);
    }

    public static int getBits(int value, int byteSize, int startBit, int stopBit, ByteOrder order){
        if (!(0 <= startBit && startBit < stopBit  && startBit <= byteSize * 8)){
            throw new IllegalArgumentException("invalid arg. startBit:" + startBit + " stopBit:" + stopBit);
        }

        int result = 0;
        int mask = 0;
        if (order == ByteOrder.BIG_ENDIAN){
            mask = mask(byteSize, startBit, stopBit, order);
            result = (value ) & mask;
            int shift = byteSize * 8 - stopBit;
            result = result >>> shift;
        } else {
            mask = mask(byteSize, startBit, stopBit, order);
            result = (value ) & mask;
            result = result >>> (startBit);
        }

        if (DEBUG){
            System.err.println(TAG + " getBits value   :" + value + " " + intStr4Debug(value));
            System.err.println(TAG + " getBits startBit:" + startBit + " stopBit:" + stopBit + " order:" + order);
            System.err.println(TAG + " getBits result  :" + result);
        }
        return result;
    }

    /**
     * 按照开始startBit位置（包括）到结束stopBit位置（不包括）获取对应掩码
     * 0 <= startbit < stopbit <= 32
     * @param startBit 开始bit 位置（包括）
     * @param stopBit  停止bit 位置（不包括）
     * @param order 指定字节序
     * @return
     */
    public static int intMask(int startBit, int stopBit, ByteOrder order){
        return mask(4, startBit, stopBit, order);
    }

    /**
     * 按照开始startBit位置（包括）到结束stopBit位置（不包括）获取对应掩码
     * 0 <= startbit < stopbit <= 8
     * @param startBit 开始bit 位置（包括）
     * @param stopBit  停止bit 位置（不包括）
     * @param order 指定字节序
     * @return
     */
    public static int byteMask(int startBit, int stopBit, ByteOrder order){
        return mask(1, startBit, stopBit, order);
    }

    @VisibleForTesting(otherwise = PRIVATE)
    public static int mask(int byteSize, int startBit, int stopBit, ByteOrder order){
        if (!(0 <= startBit && startBit < stopBit && stopBit <= 32)){
            throw new IllegalArgumentException("invalid startBit:" + startBit + " stopBit:" + stopBit);
        }

        int mask = 0;

        if (order == ByteOrder.BIG_ENDIAN){
            for (int i = startBit ; /*startBit != stopBit &&*/ i < stopBit ; i++){
                int shift = byteSize * 8 - i - 1;
                mask |= (1 << shift);
            }
        } else {
            for (int i = startBit ; /*startBit != stopBit &&*/ i < stopBit ; i++){
                mask |= (1 << i);
            }
        }

        return mask;
    }

    /**
     * 将 value 设置到 index 指定到 byte 字节，从对应 byte 字节的 startBit （左）开始，到 stopBit （右）结束，其他 bit 保留
     * @param byteArray
     * @param index
     * @param value 即将写入的值，永远用 BIT_ENDIAN 表示
     * @param startBit 开始bit 位置（包括）
     * @param stopBit  停止bit 位置（不包括）
     * @param order 将byteArray[index] 按照指定字节序处理
     *
     *
     */
    @Deprecated
    public static void set(byte[] byteArray, int index, int value, int startBit, int stopBit, ByteOrder order){
        byte byte2Set = byteArray[index];

        byte2Set = set(byte2Set, value, startBit, stopBit, order);
        byteArray[index] = byte2Set;
    }
    /**
     * 将 value 设置到 index 指定到 byte 字节，从对应 byte 字节的 startBit （左）开始，到 stopBit （右）结束，其他 bit 保留
     * @param byte2Set
     * @param value 即将写入的值，永远用 BIT_ENDIAN 表示
     * @param startBit 开始bit 位置（包括）
     * @param stopBit  停止bit 位置（不包括）
     * @param order 将byte2Set 按照指定字节序处理
     *
     *
     */
    public static byte set(byte byte2Set, int value, int startBit, int stopBit, ByteOrder order){
        if (!(0 <= startBit && startBit < stopBit  && startBit <=8)){
            throw new IllegalArgumentException("invalid arg. startBit:" + startBit + " stopBit:" + stopBit);
        }

        if (DEBUG) {
            //+=======================123456789012345678901234567890
            System.err.println(TAG + " before set:  byte2Set:" + byte2Set + " " + intStr4Debug(byte2Set));
            System.err.println(TAG + " before set:    value:" + value + " startBit:" + startBit + " stopBit:" + stopBit + " order:" + order);
        }

        int value2Set = 0;
        int valueMask = 0;
        valueMask = byteMask(8 - (stopBit - startBit), 8, ByteOrder.BIG_ENDIAN);
        if (order == ByteOrder.BIG_ENDIAN){
            int shift = 8 - stopBit;
            value2Set = value & valueMask;
            value2Set = value2Set << shift;
        } else {
            int shift = startBit;
            value2Set = value & valueMask;
            value2Set = value2Set << shift;
        }

        if (DEBUG) {
            //+=======================123456789012345678901234567890
            System.err.println(TAG + " before set: value2Set:" + value2Set + " " + intStr4Debug(value2Set));
        }

        // 用于保留其他bit位值
        int selfMask = byteMask(startBit, stopBit, order);
        // 清除相应bit位[startbit,stopbit)的值
        int tmp = ((byte2Set & 0xFF) & (~selfMask));

        tmp = tmp | value2Set;
        byte2Set = (byte) (tmp);

        if (DEBUG) {
            //+=======================123456789012345678901234567890
            System.err.println(TAG + " after  set:  byte2Set:" + byte2Set + " " + intStr4Debug(byte2Set));
        }

        return byte2Set;
    }

    public static String intStr4Debug(int value){
        return "0b" + Integer.toBinaryString(value) + " 0x" + Integer.toHexString(value);
    }

//    public static int getIntBits(int value, int startBit, int stopBit) {
//        int fragmentValue = 0;
//        int mask = mask(startBit, stopBit, ByteOrder.LITTLE_ENDIAN);
//        fragmentValue = value & (~mask);
//        fragmentValue = fragmentValue >> startBit;
//        return fragmentValue;
//    }
}
