package ru.leventov;

import java.nio.ByteBuffer;

public interface DoubleAggregator {
    void aggregate(ByteBuffer buffer, int position, double value);
    void aggregate(TwoOffsetMemory buffer, int position, double value);
    void aggregate(TwoFinalOffsetMemory buffer, int position, double value);
    void aggregate(NoOffsetMemory buffer, long position, double value);
    void aggregate(NoOffsetTwoImplMemory buffer, long position, double value);
}
