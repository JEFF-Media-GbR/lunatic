package com.jeff_media.lunatic.data;

import org.jetbrains.annotations.NotNull;

import java.io.DataOutput;

/**
 * A simple {@link DataOutput} implementation that counts the number of bytes written and discards the actual data
 */
public class ByteCounter implements DataOutput {

    private int bytes = 0;

    /**
     * Get the number of bytes written
     * @return Number of bytes written
     */
    public int getBytes() {
        return bytes;
    }

    /**
     * Get the number of bytes required to store the specified string in UTF-8
     * @param s String to check
     * @return Number of bytes required to store the specified string in UTF-8
     */
    public static long getUTFLength(final @NotNull String s) {
        long length = 0;
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if (c >= 0x0001 && c <= 0x007F) {
                length++;
            } else if (c > 0x07FF) {
                length += 3;
            } else {
                length += 2;
            }
        }
        return length;
    }

    @Override
    public void write(final int i) {
        bytes++;
    }

    @Override
    public void write(final byte @NotNull [] bytes) {
        this.bytes += bytes.length;
    }

    @Override
    public void write(final byte @NotNull [] bytes, final int off, final int len) {
        this.bytes += len;
    }

    @Override
    public void writeBoolean(final boolean bool) {
        bytes++;
    }

    @Override
    public void writeByte(final int i) {
        bytes++;
    }

    @Override
    public void writeShort(final int i) {
        bytes += 2;
    }

    @Override
    public void writeChar(final int i) {
        bytes += 2;
    }

    @Override
    public void writeInt(final int i) {
        bytes += 4;
    }

    @Override
    public void writeLong(final long l) {
        bytes += 8;
    }

    @Override
    public void writeFloat(final float f) {
        bytes += 4;
    }

    @Override
    public void writeDouble(final double d) {
        bytes += 8;
    }

    @Override
    public void writeBytes(final String s) {
        bytes += s.length();
    }

    @Override
    public void writeChars(final String s) {
        bytes += s.length() * 2;
    }

    @Override
    public void writeUTF(final String s) {
        bytes += 2 + getUTFLength(s);
    }
}
