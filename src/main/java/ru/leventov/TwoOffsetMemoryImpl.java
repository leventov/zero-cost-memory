package ru.leventov;

import java.nio.BufferOverflowException;

public class TwoOffsetMemoryImpl implements TwoOffsetMemory {
    final Object object;
    private long heapOffset;
    private long directOffset;
    private long limit;

    public TwoOffsetMemoryImpl(Object object, long heapOffset, long directOffset, long limit) {
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

    private boolean checkBounds(long offset, int size) {
        if (offset < 0 || offset + size >= limit) {
            throw new BufferOverflowException();
        }
        return true;
    }
}
