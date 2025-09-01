package com.edusyspro.api.helper.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class L {
    private L() {}

    private static final ConcurrentMap<Class<?>, Logger> CACHE = new ConcurrentHashMap<>();

    private static Logger loggerForCaller() {
        Class<?> caller = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
                .walk(frames -> frames.filter(f -> !f.getDeclaringClass().equals(L.class))
                        .findFirst()
                        .map(StackWalker.StackFrame::getDeclaringClass)
                        .orElse(null));
        return CACHE.computeIfAbsent(caller, LoggerFactory::getLogger);
    }

    public static void info(String message, Object ... args) {
        loggerForCaller().info(message, args);
    }

    public static void warn(String msg, Object... args) {
        loggerForCaller().warn(msg, args);
    }

    public static void error(String msg, Object... args) {
        loggerForCaller().error(msg, args);
    }

    public static void debug(String msg, Object... args) {
        loggerForCaller().debug(msg, args);
    }

    public static void trace(String msg, Object... args) {
        loggerForCaller().trace(msg, args);
    }

    // overloads for Throwable if you want
    public static void error(Throwable t, String msg, Object... args) {
        loggerForCaller().error(String.format(msg, args), t);
    }
}
