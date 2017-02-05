package ru.leventov;

import java.nio.ByteBuffer;

public class DoubleSumAggregator implements DoubleAggregator {

    @Override
    public void aggregate(ByteBuffer buffer, int position, double value) {
        buffer.putDouble(position, buffer.getDouble(position) + value);
    }

    @Override
    public void aggregate(TwoOffsetMemory buffer, int position, double value) {
        buffer.putDouble(position, buffer.getDouble(position) + value);
    }

    @Override
    public void aggregate(TwoFinalOffsetMemory buffer, int position, double value) {
        buffer.putDouble(position, buffer.getDouble(position) + value);
    }

    @Override
    public void aggregate(OneOffsetMemory buffer, int position, double value) {
        buffer.putDouble(position, buffer.getDouble(position) + value);
    }

    @Override
    public void aggregate(NoOffsetMemory buffer, long position, double value) {
        buffer.putDouble(position, buffer.getDouble(position) + value);
    }

    @Override
    public void aggregate(NoOffsetTwoImplMemory buffer, long position, double value) {
        buffer.putDouble(position, buffer.getDouble(position) + value);
    }
}
