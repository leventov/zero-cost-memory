package ru.leventov;

import sun.misc.Unsafe;
import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;

public interface NoOffsetMemory {
    static NoOffsetMemory from(ByteBuffer buffer) {
        if (buffer.isDirect()) {
            return new NoOffsetMemoryImpl(
                    null, ((DirectBuffer) buffer).address(), buffer.capacity());
        } else {
            return new NoOffsetMemoryImpl(
                    buffer.array(), Unsafe.ARRAY_BYTE_BASE_OFFSET, buffer.capacity());
        }
    }

    double getDouble(long offset);
    void putDouble(long offset, double value);
    float getFloat(long offset);
    int getInt(long offset);

    long start();
}
