package ru.leventov;

import java.nio.BufferOverflowException;

public class NoOffsetMemoryImpl implements NoOffsetMemory {
    final Object object;
    final long start;
    final long end;

    public NoOffsetMemoryImpl(Object object, long start, long capacity) {
        this.object = object;
        this.start = start;
        this.end = start + capacity;
    }

    @Override
    public double getDouble(long offset) {
        assert checkBounds(offset,8);
        return UnsafeUtil.U.getDouble(object, offset);
    }

    @Override
    public void putDouble(long offset, double value) {
        assert checkBounds(offset,8);
        UnsafeUtil.U.putDouble(object, offset, value);
    }

    @Override
    public float getFloat(long offset) {
        assert checkBounds(offset,4);
        return UnsafeUtil.U.getFloat(object, offset);
    }

    @Override
    public int getInt(long offset) {
        assert checkBounds(offset,4);
        return UnsafeUtil.U.getInt(object, offset);
    }

    @Override
    public long getLong(long offset) {
        assert checkBounds(offset,8);
        return UnsafeUtil.U.getLong(object, offset);
    }

    @Override
    public void putLong(long offset, long value) {
        assert checkBounds(offset,8);
        UnsafeUtil.U.putLong(object, offset, value);
    }

    private boolean checkBounds(long offset, int size) {
        if (offset < start || offset + size >= end) {
            throw new BufferOverflowException();
        }
        return true;
    }

    @Override
    public long start() {
        return start;
    }
}
