package ru.leventov;

import sun.misc.Unsafe;
import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;

public interface OneOffsetMemory {
    static OneOffsetMemory from(ByteBuffer buffer) {
        if (buffer.isDirect()) {
            return new OneOffsetMemoryImpl(
                    null, ((DirectBuffer) buffer).address(), buffer.capacity());
        } else {
            return new OneOffsetMemoryImpl(
                    buffer.array(), Unsafe.ARRAY_BYTE_BASE_OFFSET, buffer.capacity());
        }
    }

    double getDouble(long offset);
    void putDouble(long offset, double value);
    float getFloat(long offset);
    int getInt(long offset);
}
