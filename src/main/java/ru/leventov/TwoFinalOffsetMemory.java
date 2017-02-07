package ru.leventov;

import sun.misc.Unsafe;
import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;

public interface TwoFinalOffsetMemory {
    static TwoFinalOffsetMemory from(ByteBuffer buffer) {
        if (buffer.isDirect()) {
            return new TwoFinalOffsetMemoryImpl(
                    null, 0, ((DirectBuffer) buffer).address(), buffer.capacity());
        } else {
            return new TwoFinalOffsetMemoryImpl(
                    buffer.array(), Unsafe.ARRAY_BYTE_BASE_OFFSET, 0, buffer.capacity());
        }
    }

    double getDouble(long offset);
    void putDouble(long offset, double value);
    float getFloat(long offset);
    int getInt(long offset);
    void putInt(long offset, int value);
}
