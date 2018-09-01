package org.bbs.nio.bitbuffer;


import org.bbs.nio.Bits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.nio.ByteOrder;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class BitsTest {

    @Test
    public void set_8bit(){
        for (byte i = Byte.MIN_VALUE ; i < Byte.MAX_VALUE; i++){
            assertEquals(i+ " BE", i, Bits.set((byte)0, i, 0, 8, ByteOrder.BIG_ENDIAN));
            assertEquals(i+ " LE", i, Bits.set((byte)0, i, 0, 8, ByteOrder.LITTLE_ENDIAN));
        }

        byte i = Byte.MAX_VALUE;
        assertEquals(i+ " BE", i, Bits.set((byte)0, i, 0, 8, ByteOrder.BIG_ENDIAN));
        assertEquals(i+ " LE", i, Bits.set((byte)0, i, 0, 8, ByteOrder.LITTLE_ENDIAN));
    }

    @Test
    public void getByteBits(){
        for (byte i = Byte.MIN_VALUE ; i < Byte.MAX_VALUE; i++){
            assertEquals(i+ " BE", i, Bits.getByteBits(i, 0, 8, ByteOrder.BIG_ENDIAN));
            assertEquals(i+ " LE", i, Bits.getByteBits(i, 0, 8, ByteOrder.LITTLE_ENDIAN));
        }

        byte i = Byte.MAX_VALUE;
        assertEquals(i+ " BE", i, Bits.getByteBits(i, 0, 8, ByteOrder.BIG_ENDIAN));
        assertEquals(i+ " LE", i, Bits.getByteBits(i, 0, 8, ByteOrder.LITTLE_ENDIAN));
    }

    @Test
    public void getShortBits(){
        for (short i = Short.MIN_VALUE ; i < Short.MAX_VALUE ; i++) {
            assertEquals(i+ " BE", i, Bits.getShortBits(i, 0, 16, ByteOrder.BIG_ENDIAN));
            assertEquals(i+ " LE", i, Bits.getShortBits(i, 0, 16, ByteOrder.LITTLE_ENDIAN));
        }

        short i  = Short.MAX_VALUE;
        assertEquals(i+ " BE", i, Bits.getShortBits(i, 0, 16, ByteOrder.BIG_ENDIAN));
        assertEquals(i+ " LE", i, Bits.getShortBits(i, 0, 16, ByteOrder.LITTLE_ENDIAN));
    }

    @Test
    public void getIntBits() {
        final int RANGE = 100000;
        int stopPositon = Integer.MIN_VALUE + RANGE;
        for (int i = Integer.MIN_VALUE; i < stopPositon; i++) {
            assertEquals(i + " BE", i, Bits.getIntBits(i, 0, 32, ByteOrder.BIG_ENDIAN));
            assertEquals(i + " LE", i, Bits.getIntBits(i, 0, 32, ByteOrder.LITTLE_ENDIAN));
        }

        stopPositon = Integer.MAX_VALUE - RANGE;
        for (int i = Integer.MAX_VALUE; i >= stopPositon; i--) {
            assertEquals(i + " BE", i, Bits.getIntBits(i, 0, 32, ByteOrder.BIG_ENDIAN));
            assertEquals(i + " LE", i, Bits.getIntBits(i, 0, 32, ByteOrder.LITTLE_ENDIAN));
        }

        for (int i = Short.MIN_VALUE; i <= Short.MAX_VALUE; i++) {
            assertEquals(i + " BE", i, Bits.getIntBits(i, 0, 32, ByteOrder.BIG_ENDIAN));
            assertEquals(i + " LE", i, Bits.getIntBits(i, 0, 32, ByteOrder.LITTLE_ENDIAN));
        }
    }

    @Test
    public void mask() {

        final int COUNT_PER_TC = 6;
        Object[] testVector = new Object[]{
             // label,byteSize,startbit,stopbit,maskInBigEndian,maskInLittleEndian
                // intmask
                "1st",   4, 0,  1, 0x80000000, 0x1,
                "2nd",   4, 1,  2, 0x40000000, 0x2,
                "3rd",   4, 2,  3, 0x20000000, 0x4,
                "4th",   4,  3,  4, 0x10000000, 0x8,
                "5th",   4,  4,  5, 0x8000000, 0x10,
                "6th",   4,  5,  6, 0x4000000, 0x20,
                "7th",   4,  6,  7, 0x2000000, 0x40,
                "8th",   4,  7,  8, 0x1000000, 0x80,
                "9th",   4,  8,  9, 0x800000, 0x100,
                "10th",   4,  9,  10, 0x400000, 0x200,
                "11th",   4,  10,  11, 0x200000, 0x400,
                "12th",   4,  11,  12, 0x100000, 0x800,
                "13th",   4,  12,  13, 0x80000, 0x1000,
                "14th",   4,  13,  14, 0x40000, 0x2000,
                "15th",   4,  14,  15, 0x20000, 0x4000,
                "16th",   4,  15,  16, 0x10000, 0x8000,
                "17th",   4,  16,  17, 0x8000, 0x10000,
                "18th",   4,  17,  18, 0x4000, 0x20000,
                "19th",   4,  18,  19, 0x2000, 0x40000,
                "20th",   4,  19,  20, 0x1000, 0x80000,
                "21th",   4,  20,  21, 0x800, 0x100000,
                "22th",   4,  21,  22, 0x400, 0x200000,
                "23th",   4,  22,  23, 0x200, 0x400000,
                "24th",   4,  23,  24, 0x100, 0x800000,
                "25th",   4,  24,  25, 0x80, 0x1000000,
                "26th",   4,  25,  26, 0x40, 0x2000000,
                "27th",   4,  26,  27, 0x20, 0x4000000,
                "28th",   4,  27,  28, 0x10, 0x8000000,
                "29th",   4,  28,  29, 0x8, 0x10000000,
                "30th",   4,  29,  30, 0x4, 0x20000000,
                "31th",   4,  30,  31, 0x2, 0x40000000,
                "32th",   4,  31,  32, 0x1, 0x80000000,
                "33th",   4,  0,  32, 0xFFFFFFFF, 0xFFFFFFFF,
                "34th",   4,  0,  8, 0xFF000000, 0x000000FF,
                "35th",   4,  8,  16, 0xFF0000, 0x0000FF00,

                // bytemask
                "133th",   1, 0,  1, 0x80, 0x1,
                "134th",   1, 1,  2, 0x40, 0x2,
                "135th",   1, 2,  3, 0x20, 0x4,
                "136th",   1,  3,  4, 0x10, 0x8,
                "137th",   1,  4,  5, 0x8, 0x10,
                "138th",   1,  5,  6, 0x4, 0x20,
                "139th",   1,  6,  7, 0x2, 0x40,
                "140th",   1,  7,  8, 0x1, 0x80,

                "141th",  1,  0,  8, 0xFF, 0xFF,


                "141th",  1,  0,  8, 0xFF, 0xFF,

        };
        for (int i = 0; i < testVector.length / COUNT_PER_TC ; i++ ){
            String label = (String) testVector[COUNT_PER_TC * i];
            int byteSize = (int) testVector[COUNT_PER_TC * i + 1];
            int startBit = (int) testVector[COUNT_PER_TC * i + 2];
            int stopBit = (int) testVector[COUNT_PER_TC * i + 3];
            String tcLabel = "index:" + i + " " + label;

            int expect = (int) testVector[COUNT_PER_TC * i + 4];
            int actual = Bits.mask(byteSize, startBit, stopBit, ByteOrder.BIG_ENDIAN);
            assertEquals(tcLabel + " bigEndian", Integer.toBinaryString(expect), Integer.toBinaryString(actual));

            expect = (int) testVector[COUNT_PER_TC * i + 5];
            actual = Bits.mask(byteSize, startBit, stopBit, ByteOrder.LITTLE_ENDIAN);
            assertEquals(tcLabel + " littleEndian", Integer.toBinaryString(expect), Integer.toBinaryString(actual));
        }
    }


    @Test
    public void getBits() {
        final int COUNT_PER_TC = 7;
        Object[] testVector = new Object[]{
                // label,byteSize,value,startbit,stopbit,valueInBigEndian,valueInLittleEndian
                "1st",      4,  1,     0,   1,  0,  1,
                "1st-1",    4,  1,     31,  32, 1,  0,
                "2nd",      4,  2,     1,   2,  0,  1,
                "2nd-1",    4,  2,     30,  31, 1,  0,
                "3rd",      4,  4,     2,   3,  0,  1,
                "3rd-1",    4,  4,     29,  30, 1,  0,
                "4th",      4,  8,     3,   4,  0,  1,
                "4th-1",    4,  8,     28,  29, 1,  0,
                "5th",      4,  16,    4,   5,  0,  1,
                "5th-1",    4,  16,    27,  28, 1,  0,
                "6th",      4,  0x20,  5,   6,  0,  1,
                "6th-1",    4,  0x20,  26,  27, 1,  0,
                "7th",      4,  0x40,  6,   7,  0,  1,
                "7th-1",    4,  0x40,  25,  26, 1,  0,
                "8th",      4,  0x80,  7,   8,  0,  1,
                "8th-1",    4,  0x80,  24,  25, 1,  0,
                "9th",      4,  0x100,  8,   9,  0,  1,
                "9th-1",    4,  0x100,  23,  24, 1,  0,
                "10th",     4,  0x200,  9,  10, 0,  1,
                "10th-1",   4,  0x200,  22,  23,  1, 0,
                "11th",     4,  0x400, 10, 11, 0,  1,
                "11th-1",   4,  0x400, 21, 22,  1, 0,
                "12th",     4,  0x800, 11, 12, 0,  1,
                "12th-1",   4,  0x800, 20, 21,  1, 0,
                "13th",     4,  0x1000,12, 13, 0,  1,
                "13th-1",   4,  0x1000,19, 20,  1, 0,
                "14th",     4,  0x2000,13, 14, 0,  1,
                "14th-1",   4,  0x2000,18, 19,  1, 0,
                "15th",     4,  0x4000,14, 15, 0,  1,
                "15th-1",   4,  0x4000,17, 18,  1, 0,
                "16th",     4,  0x8000,15, 16, 0,  1,
                "16th-1",   4,  0x8000,16, 17,  1, 0,
                "17th",     4,  0x10000,    16, 17, 0,  1,
                "17th-1",   4,  0x10000,    15, 16, 1, 0,
                "18th",     4,   0x20000,   17, 18, 0,  1,
                "18th",     4,   0x20000,   14, 15,  1, 0,
                "19th",     4,   0x40000,   18, 19, 0,  1,
                "19th-1",   4,   0x40000,   13, 14,  1, 0,
                "20th",     4,   0x80000,   19, 20, 0,  1,
                "20th-1",   4,   0x80000,   12, 13,  1, 0,
                "21th",     4,   0x100000,  20, 21, 0,  1,
                "21th-1",   4,   0x100000,  11, 12,  1, 0,
                "22th",     4,   0x200000,  21, 22, 0,  1,
                "22th-1",   4,   0x200000,  10, 11,  1, 0,
                "23th",     4,   0x400000,  22, 23, 0,  1,
                "23th-1",   4,   0x400000,  9, 10,  1, 0,
                "24th",     4,   0x800000,  23, 24, 0,  1,
                "24th-1",   4,   0x800000,  8, 9,  1, 0,
                "25th",     4,   0x1000000, 24, 25, 0,  1,
                "25th-1",   4,   0x1000000, 7, 8,  1, 0,
                "26th",     4,   0x2000000, 25, 26, 0,  1,
                "26th-1",   4,   0x2000000, 6, 7,  1, 0,
                "27th",     4,   0x4000000, 26, 27, 0,  1,
                "27th-1",   4,   0x4000000, 5, 6,  1, 0,
                "28th",     4,   0x8000000, 27, 28, 0,  1,
                "28th-1",   4,   0x8000000,4, 5,  1, 0,
                "29th",     4,   0x10000000,28, 29, 0,  1,
                "29th-1",   4,   0x10000000,3, 4, 1, 0,
                "20th",     4,   0x20000000,29, 30, 0,  1,
                "20th-1",   4,   0x20000000,2, 3, 1, 0,
                "31th",     4,   0x40000000,30, 31, 0,  1,
                "31th-1",   4,   0x40000000,1, 2,  1, 0,
                //边界测试
                "32th",     4,   0x80000000,31, 32, 0,  1,
                "32th-1",   4,   0x80000000,0, 1,  1, 0,

                // mesh
                "mesh#3.1.1 1", 1, 0x76, 0, 4, 7, 6,
                "mesh#3.1.1 2", 1, 0x76, 4, 8, 6, 7,


                "setint", 4,  1,     30,  31,  0,  0,
        };
        String label = "";
        int value;
        for (int i = 0; i < testVector.length / COUNT_PER_TC ; i++ ){
            label = (String) testVector[COUNT_PER_TC * i];
            if (label.equalsIgnoreCase("setint")){
                value = 0;
            }
            int byteSize = (int) testVector[COUNT_PER_TC * i + 1];
            value = (int) testVector[COUNT_PER_TC * i + 2];
            int startBit = (int) testVector[COUNT_PER_TC * i + 3];
            int stopBit = (int) testVector[COUNT_PER_TC * i + 4];
            String tcLabel = "" + label + " value:" + value + " start:" + startBit + " stop:" + stopBit;

            int expect = (int) testVector[COUNT_PER_TC * i + 5];
            int actual = Bits.getBits(value, byteSize, startBit, stopBit, ByteOrder.BIG_ENDIAN);
            assertEquals(tcLabel + " expect:" + expect + " bigEndian", expect, actual);

//            int byteAfterSet = Bits.set((byte) 0b0, expect, startBit, stopBit, ByteOrder.BIG_ENDIAN);
//            assertEquals(tcLabel + " expect:" + value + " bigEndian", value, byteAfterSet);

            expect = (int) testVector[COUNT_PER_TC * i + 6];
            actual = Bits.getBits(value, byteSize, startBit, stopBit, ByteOrder.LITTLE_ENDIAN);
            assertEquals(tcLabel + " expect:" + expect + " littleEndian", expect, actual);
        }
    }

    @Test
    public void getBits_BigEndian_BG_0() {
        int expected = 0b0000_1111;
        byte[] bytes = new byte[]{0b0111_1111};
        int actual = 0;

        expected = 0b0000_0000;
        bytes = new byte[]{0b0111_1010};
        actual = Bits.getByteBits(bytes[0], 0, 1, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));

        expected = 0b0000_0001;
        bytes = new byte[]{0b0111_1000};
        actual = Bits.getByteBits(bytes[0], 1, 2, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));

        expected = 0b0000_1010;
        bytes = new byte[]{0b0111_1010};
        actual = Bits.getByteBits(bytes[0], 4, 8, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));

        expected = 0b0000_1011;
        bytes = new byte[]{0b0111_1011};
        actual = Bits.getByteBits(bytes[0], 4, 8, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));

        expected = 0b0000_1000;
        bytes = new byte[]{0b0111_1000};
        actual = Bits.getByteBits(bytes[0], 4, 8, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));

        expected = 0b0000_1000;
        bytes = new byte[]{0b0000_1000};
        actual = Bits.getByteBits(bytes[0], 1, 8, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));

        expected = 0b000_100;
        bytes = new byte[]{0b0000_1000};
        actual = Bits.getByteBits(bytes[0], 1, 7, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));

        expected = 0b0000_100;
        bytes = new byte[]{0b0000_1000};
        actual = Bits.getByteBits(bytes[0], 0, 7, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));

        expected = 0b0000_1000;
        bytes = new byte[]{0b0000_1000};
        actual = Bits.getByteBits(bytes[0], 0, 8, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));
    }

    @Test
    public void getBits_BigEndian_0() {
        int expected = 0b0000_0000;
        byte[] bytes = new byte[]{0b0000_0000};
        int actual = Bits.getByteBits(bytes[0], 4, 7, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));

        expected = 0b0000_0000;
        bytes = new byte[]{0b0000_0000};
        actual = Bits.getByteBits(bytes[0], 2, 6, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));
    }

    @Test
    public void getBits_BigEndian_LT_0() {
        int expected = 0b0000_0001;
        byte[] bytes = new byte[]{-127}; // 0b1000_0001
        int actual = Bits.getByteBits(bytes[0], 4, 8, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));

        expected = 0b100_0000;
        bytes = new byte[]{-127};// 0b1000_0001
        actual = Bits.getByteBits(bytes[0], 0, 7, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));

        expected = 0b01;
        bytes = new byte[]{-127};// 0b1000_0001
        actual = Bits.getByteBits(bytes[0], 0, 1, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));

        expected = 0b01;
        bytes = new byte[]{-126}; // 0b1000_0010
        actual = Bits.getByteBits(bytes[0], 0, 1, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));
        expected = 0b00;
        bytes = new byte[]{-126}; // 0b1000_0010
        actual = Bits.getByteBits(bytes[0], 1, 2, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));
        expected = 0b01;
        bytes = new byte[]{-126}; // 0b1000_0010
        actual = Bits.getByteBits(bytes[0], 6, 7, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));

        expected = 0b10;
        bytes = new byte[]{-127};
        actual = Bits.getByteBits(bytes[0], 0, 2, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));

        expected = -127;
        bytes = new byte[]{-127};
        actual = Bits.getByteBits(bytes[0], 0, 8, ByteOrder.BIG_ENDIAN);
        assertEquals((expected), (actual));
    }

    @Test
    public void getBits_LittleEndian_BG_0() {
        int expected = 0b0000_0111;
        byte[] bytes = new byte[]{0b0111_1111};
        int actual = Bits.getByteBits(bytes[0], 4, 7, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));


        expected = 0b0000_0000;
        bytes = new byte[]{0b0111_1010};
        actual = Bits.getByteBits(bytes[0], 0, 1, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));

        expected = 0b0000_0001;
        bytes = new byte[]{0b0111_1010};
        actual = Bits.getByteBits(bytes[0], 1, 2, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));

        expected = 0b0000_0111;
        bytes = new byte[]{0b0111_1010};
        actual = Bits.getByteBits(bytes[0], 4, 7, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));

        expected = 0b0000_0101;
        bytes = new byte[]{0b0101_1011};
        actual = Bits.getByteBits(bytes[0], 4, 7, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));

        expected = 0b0000_0100;
        bytes = new byte[]{0b0100_1000};
        actual = Bits.getByteBits(bytes[0], 4, 7, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));

        expected = 0b0000_100;
        bytes = new byte[]{0b0000_1000};
        actual = Bits.getByteBits(bytes[0], 1, 7, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));

        expected = 0b000_100;
        bytes = new byte[]{0b0000_1000};
        actual = Bits.getByteBits(bytes[0], 1, 6, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));

        expected = 0b0010_1000;
        bytes = new byte[]{0b0010_1000};
        actual = Bits.getByteBits(bytes[0], 0, 6, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));

        expected = 0b0110_1000;
        bytes = new byte[]{0b0110_1000};
        actual = Bits.getByteBits(bytes[0], 0, 7, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));
    }

    @Test
    public void getBits_LittleEndian_0() {
        int expected = 0b0000_0000;
        byte[] bytes = new byte[]{0b0000_0000};
        int actual = Bits.getByteBits(bytes[0], 4, 7, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));

        expected = 0b0000_0000;
        bytes = new byte[]{0b0000_0000};
        actual = Bits.getByteBits(bytes[0], 2, 6, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));

        expected = 0b0000_0000;
        bytes = new byte[]{0b0000_0000};
        actual = Bits.getByteBits(bytes[0], 0, 7, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));
    }

    @Test
    public void getBits_LittleEndian_LT_0() {
        int expected = 0b0000_1000;
        byte[] bytes = new byte[]{-127}; // 0b1000_0001
        int actual = Bits.getByteBits(bytes[0], 4, 8, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));

        expected = 0b00_0001;
        bytes = new byte[]{-127};// 0b1000_0001
        actual = Bits.getByteBits(bytes[0], 0, 6, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));

        expected = 0b01;
        bytes = new byte[]{-127};// 0b1000_0001
        actual = Bits.getByteBits(bytes[0], 0, 1, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actual));
    }

    @Test
    public void set() {
        byte[] array = null;
        final int INDEX = 0;
        int actual;
        int expected;


        final int COUNT_PER_TC = 6;
        Object[] testVector = new Object[]{
                // label,       value,      startBit,       stopBit,    expectedInBE,       expectedInLE
                "blemesh#3.1.1",6,      0,      4,      0x60,       0x06,
                "0st",          1,      0,      1,      0x80,      1,
                "2nd",          1,      1,      2,      0x40,      2,
                "3rd",          1,      2,      3,      0x20,      4,
                "4th",          1,      3,      4,      0x10,      8,
                "5th",          1,      4,      5,      0x8,       0x10,
                "6th",          1,      5,      6,      0x4,       0x20,
                "7th",          1,      6,      7,      0x2,       0x40,
                "8th",          1,      7,      8,      0x1,       0x80,

                "9th",          0b11,   0,      2,      0xC0,      0x3,
                "10th",         0b11,   1,      3,      0x60,      0x6,
                "11th",         0b11,   2,      4,      0x30,      0xC,
                "12th",         0b11,   3,      5,      0x18,      0x18,
                "13th",         0b11,   4,      6,      0x0C,      0x30,
                "14th",         0b11,   5,      7,      0x06,      0x60,
                "16th",         0b11,   6,      8,      0x03,      0xC0,

                "17th",         0b111,  0,      3,      0b1110_0000,0b0000_0111,
                "18th",         0b111,  1,      4,      0b0111_0000,0b0000_1110,
                "19th",         0b111,  2,      5,      0b0011_1000,0b0001_1100,
                "20th",         0b111,  3,      6,      0b0001_1100,0b0011_1000,
                "21th",         0b111,  4,      7,      0b0000_1110,0b0111_0000,
                "22th",         0b111,  5,      8,      0b0000_0111,0b1110_0000,

                "23th",         0b1111, 0,      4,      0b1111_0000,0b0000_1111,
                "24th",         0b1111, 1,      5,      0b0111_1000,0b0001_1110,
                "25th",         0b1111, 2,      6,      0b0011_1100,0b0011_1100,
                "26th",         0b1111, 3,      7,      0b0001_1110,0b0111_1000,
                "27th",         0b1111, 4,      8,      0b0000_1111,0b1111_0000,

        };

        String label = "";
        for (int i = 0 ; i < testVector.length / COUNT_PER_TC ; i++){
            if (i == 0){
                label = "4 debug";
            }
            label = (String) testVector[i * COUNT_PER_TC];
            int value = (int) testVector[i * COUNT_PER_TC + 1];
            int startBit = (int) testVector[i * COUNT_PER_TC + 2];
            int stopBit = (int) testVector[i * COUNT_PER_TC + 3];
            label += " value:" + value + " startBit:" + startBit + " stopBit:" + stopBit;

            expected = (int) testVector[i * COUNT_PER_TC + 4];
            array = new byte[]{0};
            Bits.set(array, INDEX, value, startBit, stopBit, ByteOrder.BIG_ENDIAN);
            actual = array[INDEX] & 0xFF;
            assertEquals(label + " bigEndian", expected, actual);

            expected = (int) testVector[i * COUNT_PER_TC + 5];
            array = new byte[]{0};
            Bits.set(array, INDEX, value, startBit, stopBit, ByteOrder.LITTLE_ENDIAN);
            actual = array[INDEX] & 0xFF;
            assertEquals(label + " littleEndian", expected, actual);

        }
    }
    @Test
    public void set_BigEndian() {
        byte[] value = null;
        final int INDEX = 0;

        int expect = 0;
        int actual = 0;

        expect = 0b0100_0000;
        value = new byte[]{0};
        Bits.set(value, INDEX, 1, 1, 2, ByteOrder.BIG_ENDIAN);
        actual = value[INDEX];
        assertEquals(Integer.toBinaryString(expect), Integer.toBinaryString(actual));
        expect = 0b0010_0000;
        value = new byte[]{0};
        Bits.set(value, INDEX, 1, 2, 3, ByteOrder.BIG_ENDIAN);
        actual = value[INDEX];
        assertEquals(Integer.toBinaryString(expect), Integer.toBinaryString(actual));
        expect = 0b0001_0000;
        value = new byte[]{0};
        Bits.set(value, INDEX, 1, 3, 4, ByteOrder.BIG_ENDIAN);
        actual = value[INDEX];
        assertEquals(Integer.toBinaryString(expect), Integer.toBinaryString(actual));
        expect = 0b0000_1000;
        value = new byte[]{0};
        Bits.set(value, INDEX, 1, 4, 5, ByteOrder.BIG_ENDIAN);
        actual = value[INDEX];
        assertEquals(Integer.toBinaryString(expect), Integer.toBinaryString(actual));
        expect = 0b0000_0100;
        value = new byte[]{0};
        Bits.set(value, INDEX, 1, 5, 6, ByteOrder.BIG_ENDIAN);
        actual = value[INDEX];
        assertEquals(Integer.toBinaryString(expect), Integer.toBinaryString(actual));
        expect = 0b0000_0010;
        value = new byte[]{0};
        Bits.set(value, INDEX, 1, 6, 7, ByteOrder.BIG_ENDIAN);
        actual = value[INDEX];
        assertEquals(Integer.toBinaryString(expect), Integer.toBinaryString(actual));
        expect = 0b0000_0001;
        value = new byte[]{0};
        Bits.set(value, INDEX, 1, 7, 8, ByteOrder.BIG_ENDIAN);
        actual = value[INDEX];
        assertEquals(Integer.toBinaryString(expect), Integer.toBinaryString(actual));

        expect = 0b0110_0000;
        value = new byte[]{0};
        Bits.set(value, INDEX, 0b11, 1, 3, ByteOrder.BIG_ENDIAN);
        actual = value[INDEX];
        assertEquals(Integer.toBinaryString(expect), Integer.toBinaryString(actual));

        expect = 0b0100_0000;
        value = new byte[]{0};
        Bits.set(value, INDEX, 0b10, 1, 3, ByteOrder.BIG_ENDIAN);
        actual = value[INDEX];
        assertEquals(Integer.toBinaryString(expect), Integer.toBinaryString(actual));


        expect = 0b0000_0010;
        value = new byte[]{0};
        Bits.set(value, INDEX, 0b10, 6, 8, ByteOrder.BIG_ENDIAN);
        actual = value[INDEX];
        assertEquals(Integer.toBinaryString(expect), Integer.toBinaryString(actual));
        expect = 0b0000_0001;
        value = new byte[]{0};
        Bits.set(value, INDEX, 0b1, 6, 8, ByteOrder.BIG_ENDIAN);
        actual = value[INDEX];
        assertEquals(Integer.toBinaryString(expect), Integer.toBinaryString(actual));
    }

    @Test
    public void set_clear_bit() {
        byte[] value = null;
        final int INDEX = 0;

        int expect = 0;
        int actual = 0;

        expect = 0x7F;
        value = new byte[]{(byte)0xFF};
        Bits.set(value, INDEX, 0, 0, 1, ByteOrder.BIG_ENDIAN);
        actual = value[INDEX];
        assertEquals(Integer.toBinaryString(expect), Integer.toBinaryString(actual));
    }

}
