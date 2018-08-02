package org.bbs.nio.bitbuffer;

import android.support.test.runner.AndroidJUnit4;

import org.bbs.nio.Bits;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.nio.ByteOrder;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BitsTest {
    @Test
    public void getBits_BigEndian_BG_0() {
        int expected = 0b0000_1111;
        byte[] bytes = new byte[]{0b0111_1111};
        int actural = Bits.getBits(bytes[0], 4, 7, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));

        expected = 0b0000_1010;
        bytes = new byte[]{0b0111_1010};
        actural = Bits.getBits(bytes[0], 4, 7, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));

        expected = 0b0000_1011;
        bytes = new byte[]{0b0111_1011};
        actural = Bits.getBits(bytes[0], 4, 7, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));

        expected = 0b0000_1000;
        bytes = new byte[]{0b0111_1000};
        actural = Bits.getBits(bytes[0], 4, 7, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));

        expected = 0b0000_1000;
        bytes = new byte[]{0b0000_1000};
        actural = Bits.getBits(bytes[0], 1, 7, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));

        expected = 0b000_100;
        bytes = new byte[]{0b0000_1000};
        actural = Bits.getBits(bytes[0], 1, 6, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));

        expected = 0b0000_100;
        bytes = new byte[]{0b0000_1000};
        actural = Bits.getBits(bytes[0], 0, 6, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));

        expected = 0b0000_1000;
        bytes = new byte[]{0b0000_1000};
        actural = Bits.getBits(bytes[0], 0, 7, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));
    }

    @Test
    public void getBits_BigEndian_0() {
        int expected = 0b0000_0000;
        byte[] bytes = new byte[]{0b0000_0000};
        int actural = Bits.getBits(bytes[0], 4, 7, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));

        expected = 0b0000_0000;
        bytes = new byte[]{0b0000_0000};
        actural = Bits.getBits(bytes[0], 2, 6, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));
    }

    @Test
    public void getBits_BigEndian_LT_0() {
        int expected = 0b0000_0001;
        byte[] bytes = new byte[]{-127}; // 0b1000_0001
        int actural = Bits.getBits(bytes[0], 4, 7, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));

        expected = 0b100_0000;
        bytes = new byte[]{-127};
        actural = Bits.getBits(bytes[0], 0, 6, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));

        expected = 0b10;
        bytes = new byte[]{-127};
        actural = Bits.getBits(bytes[0], 0, 1, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));

        expected = -127 & 0xFF;
        bytes = new byte[]{-127};
        actural = Bits.getBits(bytes[0], 0, 7, ByteOrder.BIG_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));
    }

    @Test
    public void getBits_LittleEndian_BG_0() {
        int expected = 0b0000_0111;
        byte[] bytes = new byte[]{0b0111_1111};
        int actural = Bits.getBits(bytes[0], 4, 7, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));

        expected = 0b0000_0111;
        bytes = new byte[]{0b0111_1010};
        actural = Bits.getBits(bytes[0], 4, 7, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));

        expected = 0b0000_0101;
        bytes = new byte[]{0b0101_1011};
        actural = Bits.getBits(bytes[0], 4, 7, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));

        expected = 0b0000_0100;
        bytes = new byte[]{0b0100_1000};
        actural = Bits.getBits(bytes[0], 4, 7, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));

        expected = 0b0000_100;
        bytes = new byte[]{0b0000_1000};
        actural = Bits.getBits(bytes[0], 1, 7, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));

        expected = 0b000_100;
        bytes = new byte[]{0b0000_1000};
        actural = Bits.getBits(bytes[0], 1, 6, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));

        expected = 0b0010_1000;
        bytes = new byte[]{0b0010_1000};
        actural = Bits.getBits(bytes[0], 0, 6, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));

        expected = 0b0110_1000;
        bytes = new byte[]{0b0110_1000};
        actural = Bits.getBits(bytes[0], 0, 7, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));
    }

    @Test
    public void getBits_LittleEndian_0() {
        int expected = 0b0000_0000;
        byte[] bytes = new byte[]{0b0000_0000};
        int actural = Bits.getBits(bytes[0], 4, 7, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));

        expected = 0b0000_0000;
        bytes = new byte[]{0b0000_0000};
        actural = Bits.getBits(bytes[0], 2, 6, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));

        expected = 0b0000_0000;
        bytes = new byte[]{0b0000_0000};
        actural = Bits.getBits(bytes[0], 0, 7, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));
    }

    @Test
    public void getBits_LittleEndian_LT_0() {
        int expected = 0b0000_1000;
        byte[] bytes = new byte[]{-127}; // 0b1000_0001
        int actural = Bits.getBits(bytes[0], 4, 7, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));

        expected = 0b00_0001;
        bytes = new byte[]{-127};// 0b1000_0001
        actural = Bits.getBits(bytes[0], 0, 6, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));

        expected = 0b01;
        bytes = new byte[]{-127};// 0b1000_0001
        actural = Bits.getBits(bytes[0], 0, 1, ByteOrder.LITTLE_ENDIAN);
        assertEquals(Integer.toBinaryString(expected), Integer.toBinaryString(actural));
    }
}
