package ru.leventov;

import org.openjdk.jmh.annotations.*;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@OperationsPerInvocation(ZeroCostMemoryBenchmark.SIZE)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Fork(1)
@State(Scope.Benchmark)
public class ZeroCostMemoryBenchmark {

    public static final int SIZE = 64 * 1024;
    private static final DoubleAggregator aggregator = new DoubleSumAggregator();

    @Param({"direct", "heap"})
    public String alloc;

    public ByteBuffer allocate(int size) {
        return alloc.equals("direct") ? ByteBuffer.allocateDirect(size) : ByteBuffer.allocate(size);
    }

    @State(Scope.Thread)
    public static class ByteBufferState {
        private ByteBuffer dimensionBuffer;
        private ByteBuffer metricBuffer;
        private int[] positions;
        private ByteBuffer targetBuffer;

        @Setup
        public void init(ZeroCostMemoryBenchmark b) {
            dimensionBuffer = b.allocate(SIZE * 4);
            metricBuffer = b.allocate(SIZE * 4);
            positions = new int[] {-1};
            targetBuffer = b.allocate(8);
        }
    }

    @Benchmark
    public double processByteBuffer(ByteBufferState s) {
        ByteBuffer dimensionBuffer = s.dimensionBuffer;
        ByteBuffer metricBuffer = s.metricBuffer;
        int[] positions = s.positions;
        ByteBuffer targetBuffer = s.targetBuffer;
        int positionOffset = 0;
        for (int i = 0; i < SIZE * 4; i += 4) {
            int dimValue = dimensionBuffer.getInt(i);
            int position = positions[dimValue];
            if (position >= 0) {
                aggregator.aggregate(targetBuffer, position, metricBuffer.getFloat(i));
            } else {
                positions[dimValue] = positionOffset;
                positionOffset += 8;
            }
        }
        return targetBuffer.getDouble(0);
    }

    @State(Scope.Thread)
    public static class TwoOffsetMemoryState {
        private TwoOffsetMemory dimensionMemory;
        private TwoOffsetMemory metricMemory;
        private TwoOffsetMemory targetMemory;

        @Setup
        public void init(ByteBufferState s) {
            dimensionMemory = TwoOffsetMemory.from(s.dimensionBuffer);
            metricMemory = TwoOffsetMemory.from(s.metricBuffer);
            targetMemory = TwoOffsetMemory.from(s.targetBuffer);
        }
    }

    @Benchmark
    public double processTwoOffsetMemory(ByteBufferState s0, TwoOffsetMemoryState s) {
        TwoOffsetMemory dimensionMemory = s.dimensionMemory;
        TwoOffsetMemory metricMemory = s.metricMemory;
        int[] positions = s0.positions;
        TwoOffsetMemory targetMemory = s.targetMemory;
        int positionOffset = 0;
        for (int i = 0; i < SIZE * 4; i += 4) {
            int dimValue = dimensionMemory.getInt(i);
            int position = positions[dimValue];
            if (position >= 0) {
                aggregator.aggregate(targetMemory, position, metricMemory.getFloat(i));
            } else {
                positions[dimValue] = positionOffset;
                positionOffset += 8;
            }
        }
        return targetMemory.getDouble(0);
    }

    @State(Scope.Thread)
    public static class TwoFinalOffsetMemoryState {
        private TwoFinalOffsetMemory dimensionMemory;
        private TwoFinalOffsetMemory metricMemory;
        private TwoFinalOffsetMemory targetMemory;

        @Setup
        public void init(ByteBufferState s) {
            dimensionMemory = TwoFinalOffsetMemory.from(s.dimensionBuffer);
            metricMemory = TwoFinalOffsetMemory.from(s.metricBuffer);
            targetMemory = TwoFinalOffsetMemory.from(s.targetBuffer);
        }
    }

    @Benchmark
    public double processTwoFinalOffsetMemory(ByteBufferState s0, TwoFinalOffsetMemoryState s) {
        TwoFinalOffsetMemory dimensionMemory = s.dimensionMemory;
        TwoFinalOffsetMemory metricMemory = s.metricMemory;
        int[] positions = s0.positions;
        TwoFinalOffsetMemory targetMemory = s.targetMemory;
        int positionOffset = 0;
        for (int i = 0; i < SIZE * 4; i += 4) {
            int dimValue = dimensionMemory.getInt(i);
            int position = positions[dimValue];
            if (position >= 0) {
                aggregator.aggregate(targetMemory, position, metricMemory.getFloat(i));
            } else {
                positions[dimValue] = positionOffset;
                positionOffset += 8;
            }
        }
        return targetMemory.getDouble(0);
    }

    @State(Scope.Thread)
    public static class OneOffsetMemoryState {
        private OneOffsetMemory dimensionMemory;
        private OneOffsetMemory metricMemory;
        private OneOffsetMemory targetMemory;

        @Setup
        public void init(ByteBufferState s) {
            dimensionMemory = OneOffsetMemory.from(s.dimensionBuffer);
            metricMemory = OneOffsetMemory.from(s.metricBuffer);
            targetMemory = OneOffsetMemory.from(s.targetBuffer);
        }
    }

    @Benchmark
    public double processOneOffsetMemory(ByteBufferState s0, OneOffsetMemoryState s) {
        OneOffsetMemory dimensionMemory = s.dimensionMemory;
        OneOffsetMemory metricMemory = s.metricMemory;
        int[] positions = s0.positions;
        OneOffsetMemory targetMemory = s.targetMemory;
        int positionOffset = 0;
        for (int i = 0; i < SIZE * 4; i += 4) {
            int dimValue = dimensionMemory.getInt(i);
            int position = positions[dimValue];
            if (position >= 0) {
                aggregator.aggregate(targetMemory, position, metricMemory.getFloat(i));
            } else {
                positions[dimValue] = positionOffset;
                positionOffset += 8;
            }
        }
        return targetMemory.getDouble(0);
    }

    @State(Scope.Thread)
    public static class NoOffsetMemoryState {
        private NoOffsetMemory dimensionMemory;
        private NoOffsetMemory metricMemory;
        private long[] positions;
        private NoOffsetMemory targetMemory;

        @Setup
        public void init(ByteBufferState s) {
            dimensionMemory = NoOffsetMemory.from(s.dimensionBuffer);
            metricMemory = NoOffsetMemory.from(s.metricBuffer);
            positions = new long[] {-1};
            targetMemory = NoOffsetMemory.from(s.targetBuffer);
        }
    }

    @Benchmark
    public double processNoOffsetMemory(ByteBufferState s0, NoOffsetMemoryState s) {
        NoOffsetMemory dimensionMemory = s.dimensionMemory;
        long dimensionStart = dimensionMemory.start();
        NoOffsetMemory metricMemory = s.metricMemory;
        long metricStart = metricMemory.start();
        long[] positions = s.positions;
        NoOffsetMemory targetMemory = s.targetMemory;
        long positionOffset = 0;
        for (int i = 0; i < SIZE * 4; i += 4) {
            int dimValue = dimensionMemory.getInt(dimensionStart + i);
            long position = positions[dimValue];
            if (position >= 0) {
                aggregator.aggregate(targetMemory, position, metricMemory.getFloat(metricStart + i));
            } else {
                positions[dimValue] = targetMemory.start() + positionOffset;
                positionOffset += 8;
            }
        }
        return targetMemory.getDouble(targetMemory.start());
    }

    @State(Scope.Thread)
    public static class NoOffsetTwoImplMemoryState {
        private NoOffsetTwoImplMemory dimensionMemory;
        private NoOffsetTwoImplMemory metricMemory;
        private long[] positions;
        private NoOffsetTwoImplMemory targetMemory;

        @Setup
        public void init(ByteBufferState s) {
            dimensionMemory = NoOffsetTwoImplMemory.from(s.dimensionBuffer);
            metricMemory = NoOffsetTwoImplMemory.from(s.metricBuffer);
            positions = new long[] {-1};
            targetMemory = NoOffsetTwoImplMemory.from(s.targetBuffer);
        }
    }

    @Benchmark
    public double processNoOffsetTwoImplMemory(ByteBufferState s0, NoOffsetTwoImplMemoryState s) {
        NoOffsetTwoImplMemory dimensionMemory = s.dimensionMemory;
        long dimensionStart = dimensionMemory.start();
        NoOffsetTwoImplMemory metricMemory = s.metricMemory;
        long metricStart = metricMemory.start();
        long[] positions = s.positions;
        NoOffsetTwoImplMemory targetMemory = s.targetMemory;
        long positionOffset = 0;
        for (int i = 0; i < SIZE * 4; i += 4) {
            int dimValue = dimensionMemory.getInt(dimensionStart + i);
            long position = positions[dimValue];
            if (position >= 0) {
                aggregator.aggregate(targetMemory, position, metricMemory.getFloat(metricStart + i));
            } else {
                positions[dimValue] = targetMemory.start() + positionOffset;
                positionOffset += 8;
            }
        }
        return targetMemory.getDouble(targetMemory.start());
    }
}
