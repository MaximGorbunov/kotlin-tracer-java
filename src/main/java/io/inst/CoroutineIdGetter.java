package io.inst;

import java.lang.reflect.Field;
import kotlin.coroutines.CoroutineContext;

public class CoroutineIdGetter {
    private static final Field id;
    private static final CoroutineContext.Key<?> key;

    static {
        try {
            Class<?> coroutineIdClass = Class.forName("kotlinx.coroutines.CoroutineId");
            id = coroutineIdClass.getDeclaredField("id");
            id.setAccessible(true);
            key = (CoroutineContext.Key<?>) coroutineIdClass.getDeclaredField("Key").get(null);
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static long getCoroutineId(CoroutineContext context) {
        Object coroutineId = context.get(key);
        try {
            return (long) id.get(coroutineId);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
