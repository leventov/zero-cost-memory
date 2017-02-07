package ru.leventov;

import java.nio.BufferOverflowException;

public class DirectNoOffsetTwoImplMemoryImpl implements NoOffsetTwoImplMemory {
    final long start;
    final long end;

    public DirectNoOffsetTwoImplMemoryImpl(long start, long capacity) {
        this.start = start;
        this.end = start + capacity;
    }

    @Override
    public double getDouble(long offset) {
        assert checkBounds(offset,8);
        return UnsafeUtil.U.getDouble(offset);
    }

    @Override
    public void putDouble(long offset, double value) {
        assert checkBounds(offset,8);
        UnsafeUtil.U.putDouble(offset, value);
    }

    @Override
    public float getFloat(long offset) {
        assert checkBounds(offset,4);
        return UnsafeUtil.U.getFloat(offset);
    }

    @Override
    public int getInt(long offset) {
        assert checkBounds(offset,4);
        return UnsafeUtil.U.getInt(offset);
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
