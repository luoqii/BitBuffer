package org.bbs.nio.bitbuffer;

import org.bbs.nio.BitBuffer;
import org.bbs.nio.Bits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class BitBufferTest {

    @Test
    public void getInt_32bit() {
        int RANGE = 0xFFFF;
        RANGE = Integer.MAX_VALUE * 2 - 999;
        BitBuffer bitBuffer;
        for (int i = Integer.MIN_VALUE ;i < Integer.MIN_VALUE + RANGE;i++){
            if (i == -2147483520){
                i = i;
            }
//            System.err.println("int i:" + i + " " + Bits.intStr4Debug(i));
            bitBuffer = BitBuffer.allocate(4 * 8).order(ByteOrder.BIG_ENDIAN);
            bitBuffer.putInt(32, i);
            bitBuffer.position(0);
            assertEquals(i + " BE", i, bitBuffer.getInt(32));

            bitBuffer = BitBuffer.allocate(4 * 8).order(ByteOrder.LITTLE_ENDIAN);
            bitBuffer.putInt(32, i);
            bitBuffer.position(0);
            assertEquals(i + " BE", i, bitBuffer.getInt(32));
        }
    }

    @Test
    public void getInt_32bit_randomly() {
        BitBuffer bitBuffer;
        for (int i = 0 ;i < 10000 ;i++){
            int integer = new Random().nextInt();
            System.err.println("int integer:" + integer + " " + Bits.intStr4Debug(integer));
            bitBuffer = BitBuffer.allocate(4 * 8).order(ByteOrder.BIG_ENDIAN);
            bitBuffer.putInt(32, integer);
            bitBuffer.position(0);
            assertEquals(integer + " BE", integer, bitBuffer.getInt(32));

            bitBuffer = BitBuffer.allocate(4 * 8).order(ByteOrder.LITTLE_ENDIAN);
            bitBuffer.putInt(32, integer);
            bitBuffer.position(0);
            assertEquals(integer + " BE", integer, bitBuffer.getInt(32));
        }
    }


    @Test
    public void getInt_16bit() {
        BitBuffer bitBuffer;
        for (short i = Short.MIN_VALUE ;i < 0 ;i++){
            System.err.println("short i:" + i + " " + Bits.intStr4Debug(i));
            if (i == -32640){
                i = i;
            }
            bitBuffer = BitBuffer.allocate(2 * 8).order(ByteOrder.BIG_ENDIAN);
            bitBuffer.putInt(16, i);
            bitBuffer.position(0);
            int expected = (i & 0xFF00) + (i & 0xFF);
            assertEquals(i + " BE", expected, bitBuffer.getInt(16));

            bitBuffer = BitBuffer.allocate(2 * 8).order(ByteOrder.LITTLE_ENDIAN);
            bitBuffer.putInt(16, i);
            bitBuffer.position(0);
            assertEquals(i + " BE", expected, bitBuffer.getInt(16));
        }

        for (short i = 0 ;i < Short.MAX_VALUE ;i++){
            System.err.println("short i:" + i + " " + Bits.intStr4Debug(i));
            if (i == -32640){
                i = i;
            }
            bitBuffer = BitBuffer.allocate(2 * 8).order(ByteOrder.BIG_ENDIAN);
            bitBuffer.putInt(16, i);
            bitBuffer.position(0);
            assertEquals(i + " BE", i, bitBuffer.getInt(16));

            bitBuffer = BitBuffer.allocate(2 * 8).order(ByteOrder.LITTLE_ENDIAN);
            bitBuffer.putInt(16, i);
            bitBuffer.position(0);
            assertEquals(i + " BE", i, bitBuffer.getInt(16));
        }

        short i = Short.MAX_VALUE;
        bitBuffer = BitBuffer.allocate(2 * 8).order(ByteOrder.BIG_ENDIAN);
        bitBuffer.putInt(16, i);
        bitBuffer.position(0);
        assertEquals(i + " BE", i, bitBuffer.getInt(16));

        bitBuffer = BitBuffer.allocate(2 * 8).order(ByteOrder.LITTLE_ENDIAN);
        bitBuffer.putInt(16, i);
        bitBuffer.position(0);
        assertEquals(i + " BE", i, bitBuffer.getInt(16));
    }

    @Test
    public void getInt_8bit() {
        BitBuffer bitBuffer;
        for (byte i = Byte.MIN_VALUE;i < 0 ;i++){
            System.err.println("byte i:" + i + " " + Bits.intStr4Debug(i));
            bitBuffer = BitBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN);
            bitBuffer.putInt(8, i);
            bitBuffer.position(0);
            int expected = (i & 0xFF);
            assertEquals(i + " BE", expected, bitBuffer.getInt(8));

            bitBuffer = BitBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
            bitBuffer.putInt(8, i);
            bitBuffer.position(0);
            assertEquals(i + " BE", expected, bitBuffer.getInt(8));
        }

        for (byte i = 0;i < Byte.MAX_VALUE ;i++){
            System.err.println("byte i:" + i + " " + Bits.intStr4Debug(i));
            bitBuffer = BitBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN);
            bitBuffer.putInt(8, i);
            bitBuffer.position(0);
            assertEquals(i + " BE", i, bitBuffer.getInt(8));

            bitBuffer = BitBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
            bitBuffer.putInt(8, i);
            bitBuffer.position(0);
            assertEquals(i + " BE", i, bitBuffer.getInt(8));
        }

        int i = Byte.MAX_VALUE;
        System.err.println("byte i:" + i + " " + Bits.intStr4Debug(i));
        bitBuffer = BitBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN);
        bitBuffer.putInt(8, i);
        bitBuffer.position(0);
        assertEquals(i + " BE", i, bitBuffer.getInt(8));

        bitBuffer = BitBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
        bitBuffer.putInt(8, i);
        bitBuffer.position(0);
        assertEquals(i + " BE", i, bitBuffer.getInt(8));
    }

    @Test
    public void putInt_getInt_randomly() {
        final int TEST_COUNT = 0xFFFFFF;
        final int MAX_BYTE_SIZE = 128;

        List<int[]> testVector;
        for (int i = 0 ; i < TEST_COUNT ; i++){
            testVector = new ArrayList<>();

            int bitSize = (new Random().nextInt(MAX_BYTE_SIZE) + 1) * 8;
            int remainingBitSize = bitSize;
            while (remainingBitSize > 0) {
                int randomBitSize = new Random().nextInt(Math.min(remainingBitSize, 32)) + 1;
                int randomValue = 0;

                String binaryStr = "";
                for (int j = 0 ; j < randomBitSize; j++){
                    binaryStr += (new Random().nextBoolean() ? "1" : "0");
                }
                randomValue = (int) Long.valueOf(binaryStr, 2).longValue();

                int[] op = new int[]{randomBitSize, randomValue};
                testVector.add(op);

                remainingBitSize -= randomBitSize;
            }

//            System.err.println("test vector. bitSize:" + bitSize);
            String testVectorStr = "";
            for (int[] op: testVector){
                int value = op[1];
                System.err.println("bitSize:" + op[0] + "\tvalue:" + value + " " + Bits.intStr4Debug(value));
                testVectorStr += "\nbitSize:" + op[0] + " value:" + value;
            }

//            System.err.println("test BIG_ENDIAN");
            BitBuffer bitBuffer = BitBuffer.allocate(bitSize).order(ByteOrder.BIG_ENDIAN);
            for (int[] op: testVector){
                bitBuffer.putInt(op[0], op[1]);
            }
            bitBuffer.position(0);
            int step = 0;
            for (int[] op: testVector){
                step++;
                int expected = op[1];
                int actual = bitBuffer.getInt(op[0]);
                assertEquals(testVectorStr + "\n step:" + step + " getInt BE", expected, actual);
            }

//            System.err.println("test LITTLE_ENDIAN");
            bitBuffer = BitBuffer.allocate(bitSize).order(ByteOrder.LITTLE_ENDIAN);
            for (int[] op: testVector){
                bitBuffer.putInt(op[0], op[1]);
            }
            bitBuffer.position(0);
            step = 0;
            for (int[] op: testVector){
                step++;
                int expected = op[1];
                int actual = bitBuffer.getInt(op[0]);
                assertEquals(testVectorStr + "\n step:" + step + " getInt LE", expected, actual);
            }
        }

    }

    @Test
    public void putInt_getInt_randomly_simple() {
        final int TEST_COUNT = 10000;

        for (int i = 0 ; i < TEST_COUNT ; i++){
            int randomValue = new Random().nextInt();
            int randomBitSize = new Random().nextInt(31) + 1;
            randomValue = Bits.getIntBits(randomValue, 0, randomBitSize, ByteOrder.LITTLE_ENDIAN);
//            randomBitSize = 32;

            System.err.println("i:" + i + " randomValue:" + randomValue + " randomBitSize:" + randomBitSize);

            BitBuffer bitBuffer = BitBuffer.allocate(4 * 8).order(ByteOrder.BIG_ENDIAN);
            bitBuffer.putInt(randomBitSize, randomValue);
            bitBuffer.position(0);
            assertEquals("bitSize:" + randomBitSize + " value:" + randomValue + " BE", randomValue, bitBuffer.getInt(randomBitSize));

            bitBuffer = BitBuffer.allocate(4 * 8).order(ByteOrder.LITTLE_ENDIAN);
            bitBuffer.putInt(randomBitSize, randomValue);
            bitBuffer.position(0);
            assertEquals("bitSize:" + randomBitSize + " value:" + randomValue + " LE", randomValue, bitBuffer.getInt(randomBitSize));
        }

    }

    @Test
    public void putInt_getInt() {

        List<SetIntTestVector> vectors = new ArrayList<>();

        vectors.add(new SetIntTestVector("1byte-1",8,
                new int[]{
                        1, 1,
                        1, 0,
                        1, 1,
                        1, 0,
                        1, 1,
                        1, 0,
                        1, 1,
                        1, 0,
                }, new byte[]{(byte) 0b1010_1010}, new byte[]{0b0101_0101}));
        vectors.add(new SetIntTestVector("1byte-2",8,
                new int[]{
                        7, 1,
                        1, 1,
                }, new byte[]{(byte) 0b0000_0011}, new byte[]{(byte) 0b1000_0001}));
        vectors.add(new SetIntTestVector("1byte-3",8,
                new int[]{
                        1, 1,
                        7, 1,
                }, new byte[]{(byte) 0b1000_0001}, new byte[]{(byte) 0b0000_0011}));
        vectors.add(new SetIntTestVector("1byte-4",8,
                new int[]{
                        8, 1,
                }, new byte[]{(byte) 0b1}, new byte[]{(byte) 0b1}));
        vectors.add(new SetIntTestVector("1byte-5",8,
                new int[]{
                        8, 2,
                }, new byte[]{(byte) 2}, new byte[]{(byte) 2}));
        vectors.add(new SetIntTestVector("1byte-6",8,
                new int[]{
                        8, 0,
                }, new byte[]{(byte) 0}, new byte[]{(byte) 0}));
        vectors.add(new SetIntTestVector("1byte-7",8,
                new int[]{
                        8, 0xFF,
                }, new byte[]{(byte) 0xFF}, new byte[]{(byte) 0xFF}));
        vectors.add(new SetIntTestVector("1byte-8",8,
                new int[]{
                        8, 0b1000_0000,
                }, new byte[]{(byte) 0b1000_0000}, new byte[]{(byte) 0b1000_0000}));


        vectors.add(new SetIntTestVector("1000th",16,
                new int[]{
                        6, 0b1,
                        10, 0b1
                },
                new byte[]{0b0000_0100, 0b0000_0001},
                new byte[]{0b0100_0001, 0b0000_0000}));
        vectors.add(new SetIntTestVector("1001th",16,
                new int[]{
                        6, 0b1,
                        10, 0b10_0000_0001
                },
                new byte[]{0b0000_0110, 0b0000_0001},
                new byte[]{0b0100_0001, (byte)0b1000_0000}));
        vectors.add(new SetIntTestVector("1002th",16,
                new int[]{
                        10, 0b10_0000_0001,
                        6, 0b1,
                },
                new byte[]{(byte) 0b1000_0000, 0b0100_0001},
                new byte[]{0b0000_0001, (byte)0b0000_0110}));
        vectors.add(new SetIntTestVector("1003th",16,
                new int[]{
                        8, 0b1,
                        8, 0b1000_0001
                },
                new byte[]{0b1, (byte) 0b1000_0001},
                new byte[]{0b1, (byte) 0b1000_0001}));

        vectors.add(new SetIntTestVector("mesh#3.1.1",4 * 8,
                new int[]{
                        4,  0x6,
                        12, 0x987,
                        16, 0x1234
                },
                new byte[]{0x69, (byte) 0x87, 0x12, 0x34,},
                new byte[]{0x76, (byte) 0x98, 0x34, 0x12,}));

        for (SetIntTestVector v : vectors){
            v.doTest();
        }
    }

    public static class SetIntTestVector{
        private final int bitSize;
        private final int[] operation;
        private final byte[] expectedResultBE;

        static private final int OPS_PER_TEST = 2;
        private final String label;
        private final byte[] expectedResultLE;

        /**
         *
         * @param label
         * @param bitSize
         * @param operation operation[0] bitsize; operation[1] value
         * @param expectedResultBE
         */
        public SetIntTestVector(String label, int bitSize,int[] operation, byte[] expectedResultBE, byte[] expectedResultLE){
            this.label = label;
            this.bitSize = bitSize;
            this.operation = operation;
            this.expectedResultBE = expectedResultBE;
            this.expectedResultLE = expectedResultLE;
        }

        public void doTest(){
            BitBuffer bitBuffer = null;
            if ("1002th".equalsIgnoreCase(label)){
                bitBuffer= BitBuffer.allocate(8);
            }

            bitBuffer = BitBuffer.allocate(bitSize).order(ByteOrder.BIG_ENDIAN);
            for (int i = 0 ; i < operation.length / OPS_PER_TEST ; i++){
                int bitSize = operation[i * OPS_PER_TEST];
                int value = operation[i * OPS_PER_TEST + 1];

                bitBuffer.putInt(bitSize, value);
            }
            assertArrayEquals(label + " putInt BE", expectedResultBE, bitBuffer.array());

            bitBuffer = BitBuffer.wrap(expectedResultBE).order(ByteOrder.BIG_ENDIAN);
            for (int i = 0 ; i < operation.length / OPS_PER_TEST ; i++){
                int bitSize = operation[i * OPS_PER_TEST];
                int value = operation[i * OPS_PER_TEST + 1];
                int actual = bitBuffer.getInt(bitSize);

                assertEquals(label + " getInt BE", value, actual);
            }

            bitBuffer = BitBuffer.allocate(bitSize).order(ByteOrder.LITTLE_ENDIAN);
            for (int i = 0 ; i < operation.length / OPS_PER_TEST ; i++){
                int bitSize = operation[i * OPS_PER_TEST];
                int value = operation[i * OPS_PER_TEST + 1];

                bitBuffer.putInt(bitSize, value);
            }
            assertArrayEquals(label + " putInt LE", expectedResultLE, bitBuffer.array());

            bitBuffer = BitBuffer.wrap(expectedResultLE).order(ByteOrder.LITTLE_ENDIAN);
            for (int i = 0 ; i < operation.length / OPS_PER_TEST ; i++){
                int bitSize = operation[i * OPS_PER_TEST];
                int value = operation[i * OPS_PER_TEST + 1];
                int actual = bitBuffer.getInt(bitSize);

                assertEquals(label + " getInt LE", value, actual);
            }
        }
    }

    @Test
    public void putInt_bigendian_2byte() {
        BitBuffer bitBuffer = null;

        bitBuffer = BitBuffer.allocate(16).order(ByteOrder.BIG_ENDIAN);
        bitBuffer.putInt(6, 0b1);
        bitBuffer.putInt(10, 0b1);
        assertArrayEquals(new byte[]{0b0000_0100, 0b0000_0001}, bitBuffer.array());

    }

    @Test
    public void putInt_bigendian_1byte_1bit() {
        BitBuffer bitBuffer = null;

        bitBuffer = BitBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN);
        bitBuffer.putInt(1, 0b1);
        assertEquals(0x80, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(1, 0b1);
        assertEquals(0xC0, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(1, 0b1);
        assertEquals(0xE0, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(1, 0b1);
        assertEquals(0xF0, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(1, 0b1);
        assertEquals(0xF8, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(1, 0b1);
        assertEquals(0xFC, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(1, 0b1);
        assertEquals(0xFE, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(1, 0b1);
        assertEquals(0xFF, bitBuffer.array()[0] & 0xFF);

        bitBuffer = BitBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN);
        bitBuffer.putInt(1, 0b0);
        assertEquals(0x00, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(1, 0b1);
        assertEquals(0x40, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(1, 0b0);
        assertEquals(0x40, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(1, 0b1);
        assertEquals(0x50, bitBuffer.array()[0] & 0xFF);

        bitBuffer.putInt(1, 0b1);
        assertEquals(0x58, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(1, 0b0);
        assertEquals(0x58, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(1, 0b1);
        assertEquals(0x5a, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(1, 0b0);
        assertEquals(0x5a, bitBuffer.array()[0] & 0xFF);

        bitBuffer = BitBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN).position(4);
        bitBuffer.putInt(1, 0b1);
        assertEquals(0x08, bitBuffer.array()[0] & 0xFF);
    }

    @Test
    public void putInt_bigendian_1byte_multibit() {
        BitBuffer bitBuffer = null;

        // mesh profile # 3.1.1
        bitBuffer = BitBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN);
        bitBuffer.putInt(4, 6);
        assertEquals(0x60, bitBuffer.array()[0] & 0xFF);

        bitBuffer = BitBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN);
        bitBuffer.putInt(2, 0b01);
        assertEquals(0x40, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(2, 0b01);
        assertEquals(0x50, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(2, 0b10);
        assertEquals(0x58, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(2, 0b10);
        assertEquals(0x5a, bitBuffer.array()[0] & 0xFF);


        bitBuffer = BitBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN);
        bitBuffer.putInt(3, 0b001);
        assertEquals(0x20, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(3, 0b001);
        assertEquals(0x24, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(2, 0b01);
        assertEquals(0x25, bitBuffer.array()[0] & 0xFF);


        bitBuffer = BitBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN);
        bitBuffer.putInt(4, 0b0001);
        assertEquals(0x10, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(4, 0b0001);
        assertEquals(0x11, bitBuffer.array()[0] & 0xFF);

        bitBuffer = BitBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN);
        bitBuffer.putInt(5, 0b00001);
        assertEquals(0x08, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(3, 0b001);
        assertEquals(0x09, bitBuffer.array()[0] & 0xFF);
        bitBuffer = BitBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN);
        bitBuffer.putInt(3, 0b001);
        assertEquals(0x20, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(5, 0b00001);
        assertEquals(0x21, bitBuffer.array()[0] & 0xFF);


        bitBuffer = BitBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN);
        bitBuffer.putInt(6, 0b1);
        assertEquals(0x04, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(2, 0b1);
        assertEquals(0x05, bitBuffer.array()[0] & 0xFF);
        bitBuffer = BitBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN);
        bitBuffer.putInt(2, 0b1);
        assertEquals(0x40, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(6, 0b1);
        assertEquals(0x41, bitBuffer.array()[0] & 0xFF);
    }

    @Test
    public void putInt_littleendian_1byte_1bit() {
        BitBuffer bitBuffer = null;

        // mesh profile # 3.1.1
        bitBuffer = BitBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
        bitBuffer.putInt(4, 6);
        assertEquals(0x6, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(4, 7);
        assertEquals(0x76, bitBuffer.array()[0] & 0xFF);

        bitBuffer = BitBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
        bitBuffer.putInt(1, 0b1);
        assertEquals(0b1, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(1, 0b1);
        assertEquals(0b11, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(1, 0b1);
        assertEquals(0b111, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(1, 0b1);
        assertEquals(0b1111, bitBuffer.array()[0] & 0xFF);

        bitBuffer.putInt(1, 0b1);
        assertEquals(0b1_1111, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(1, 0b1);
        assertEquals(0b11_1111, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(1, 0b1);
        assertEquals(0b111_1111, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(1, 0b1);
        assertEquals(0b1111_1111, bitBuffer.array()[0] & 0xFF);

        bitBuffer = BitBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
        bitBuffer.putInt(1, 0b0);
        assertEquals(0b0, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(1, 0b1);
        assertEquals(0b10, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(1, 0b0);
        assertEquals(0b010, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(1, 0b1);
        assertEquals(0b1010, bitBuffer.array()[0] & 0xFF);

        bitBuffer.putInt(1, 0b1);
        assertEquals(0b1_1010, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(1, 0b0);
        assertEquals(0b01_1010, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(1, 0b1);
        assertEquals(0b101_1010, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(1, 0b0);
        assertEquals(0b101_1010, bitBuffer.array()[0] & 0xFF);

    }

    @Test
    public void putInt_littleendian_1byte_multibit() {
        BitBuffer bitBuffer = null;

        bitBuffer = BitBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
        bitBuffer.putInt(2, 0b01);
        assertEquals(0b01, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(2, 0b01);
        assertEquals(0b0101, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(2, 0b10);
        assertEquals(0b10_0101, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(2, 0b10);
        assertEquals(0b1010_0101, bitBuffer.array()[0] & 0xFF);


        bitBuffer = BitBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
        bitBuffer.putInt(3, 0b001);
        assertEquals(0b001, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(3, 0b001);
        assertEquals(0b00_1001, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(2, 0b01);
        assertEquals(0b0100_1001, bitBuffer.array()[0] & 0xFF);


        bitBuffer = BitBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
        bitBuffer.putInt(4, 0b0001);
        assertEquals(0b0001, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(4, 0b0001);
        assertEquals(0b0001_0001, bitBuffer.array()[0] & 0xFF);

        bitBuffer = BitBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
        bitBuffer.putInt(5, 0b00001);
        assertEquals(0b0_0001, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(3, 0b001);
        assertEquals(0b0010_0001, bitBuffer.array()[0] & 0xFF);
        bitBuffer = BitBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
        bitBuffer.putInt(3, 0b001);
        assertEquals(0b001, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(5, 0b00001);
        assertEquals(0b0000_1001, bitBuffer.array()[0] & 0xFF);


        bitBuffer = BitBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
        bitBuffer.putInt(6, 0b00_0001);
        assertEquals(0b00_0001, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(2, 0b01);
        assertEquals(0b0100_0001, bitBuffer.array()[0] & 0xFF);
        bitBuffer = BitBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
        bitBuffer.putInt(2, 0b01);
        assertEquals(0b01, bitBuffer.array()[0] & 0xFF);
        bitBuffer.putInt(6, 0b00_0001);
        assertEquals(0b0000_0101, bitBuffer.array()[0] & 0xFF);
    }

    @Test
    public void position(){
        BitBuffer bitBuffer;

        bitBuffer = BitBuffer.allocate(8);
        assertEquals(0, bitBuffer.position());
        bitBuffer.getInt(3);
        assertEquals(3, bitBuffer.position());
        bitBuffer.getInt(2);
        assertEquals(5, bitBuffer.position());

        bitBuffer = BitBuffer.allocate(1);
        assertEquals(0, bitBuffer.position());
        bitBuffer.getInt(3);
        assertEquals(3, bitBuffer.position());
        bitBuffer.getInt(2);
        assertEquals(5, bitBuffer.position());
    }

    @Test
    public void limit_capacity(){
        BitBuffer bitBuffer;

        bitBuffer = BitBuffer.allocate(8);
        assertEquals(8, bitBuffer.limit());
        assertEquals(8, bitBuffer.capacity());
        bitBuffer.getInt(3);
        assertEquals(8, bitBuffer.limit());
        assertEquals(8, bitBuffer.capacity());
        bitBuffer.getInt(2);
        assertEquals(8, bitBuffer.limit());
        assertEquals(8, bitBuffer.capacity());


        bitBuffer = BitBuffer.allocate(1);
        assertEquals(1, bitBuffer.limit());
        assertEquals(1, bitBuffer.capacity());
        bitBuffer.getInt(1);
        assertEquals(1, bitBuffer.limit());
        assertEquals(1, bitBuffer.capacity());
    }

    @Test(expected = BufferUnderflowException.class)
    public void put_BufferUnderflowException(){
        BitBuffer bitBuffer;
        bitBuffer = BitBuffer.allocate(8);
        bitBuffer.getInt(9);
    }

    @Test(expected = BufferOverflowException.class)
    public void put_BufferOverflowException(){
        BitBuffer bitBuffer;
        bitBuffer = BitBuffer.allocate(8);
        bitBuffer.putInt(9, 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void put_IndexOutOfBoundsException(){
        BitBuffer bitBuffer;
        bitBuffer = BitBuffer.allocate(8);
        bitBuffer.putInt(9,1, 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void set_IndexOutOfBoundsException(){
        BitBuffer bitBuffer;

        bitBuffer = BitBuffer.allocate(8);
        bitBuffer.getInt(9, 1);
    }
}
