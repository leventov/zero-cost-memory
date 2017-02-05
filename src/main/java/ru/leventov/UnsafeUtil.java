package ru.leventov;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public final class UnsafeUtil {

    public static final Unsafe U;
    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            U = (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            throw new RuntimeException("Cannot access Unsafe methods", e);
        }
    }

    private UnsafeUtil() {}
}
