package ru.leventov;

import java.nio.BufferOverflowException;

public class OneOffsetMemoryImpl implements OneOffsetMemory {
    final Object object;
    private long offset;
    private long limit;

    public OneOffsetMemoryImpl(Object object, long offset, long limit) {
        this.object = object;
        this.offset = offset;
        this.limit = limit;
    }

    @Override
    public double getDouble(long offset) {
        assert checkBounds(offset, 8);
        return UnsafeUtil.U.getDouble(object, this.offset + offset);
    }

    @Override
    public void putDouble(long offset, double value) {
        assert checkBounds(offset, 8);
        UnsafeUtil.U.putDouble(object, this.offset + offset, value);
    }

    @Override
    public float getFloat(long offset) {
        assert checkBounds(offset, 4);
        return UnsafeUtil.U.getFloat(object, this.offset + offset);
    }

    @Override
    public int getInt(long offset) {
        assert checkBounds(offset, 4);
        return UnsafeUtil.U.getInt(object, this.offset + offset);
    }

    @Override
    public void putInt(long offset, int value) {
        assert checkBounds(offset, 4);
        UnsafeUtil.U.putInt(object, this.offset + offset, value);
    }

    private boolean checkBounds(long offset, int size) {
        if (offset < 0 || offset + size >= limit) {
            throw new BufferOverflowException();
        }
        return true;
    }
}
