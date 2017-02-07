package ru.leventov;

import java.nio.BufferOverflowException;

public class TwoFinalOffsetMemoryImpl implements TwoFinalOffsetMemory {
    final Object object;
    private final long heapOffset;
    private final long directOffset;
    private final long limit;

    public TwoFinalOffsetMemoryImpl(Object object, long heapOffset, long directOffset, long limit) {
        this.object = object;
        this.heapOffset = heapOffset;
        this.directOffset = directOffset;
        this.limit = limit;
    }

    @Override
    public double getDouble(long offset) {
        assert checkBounds(offset, 8);
        return UnsafeUtil.U.getDouble(object, heapOffset + directOffset + offset);
    }

    @Override
    public void putDouble(long offset, double value) {
        assert checkBounds(offset, 8);
        UnsafeUtil.U.putDouble(object, heapOffset + directOffset + offset, value);
    }

    @Override
    public float getFloat(long offset) {
        assert checkBounds(offset, 4);
        return UnsafeUtil.U.getFloat(object, heapOffset + directOffset + offset);
    }

    @Override
    public int getInt(long offset) {
        assert checkBounds(offset, 4);
        return UnsafeUtil.U.getInt(object, heapOffset + directOffset + offset);
    }

    @Override
    public void putInt(long offset, int value) {
        assert checkBounds(offset, 4);
        UnsafeUtil.U.putInt(object, heapOffset + directOffset + offset, value);
    }

    private boolean checkBounds(long offset, int size) {
        if (offset < 0 || offset + size >= limit) {
            throw new BufferOverflowException();
        }
        return true;
    }
}
