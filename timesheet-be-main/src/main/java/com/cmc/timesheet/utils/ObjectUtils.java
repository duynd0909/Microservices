package com.cmc.timesheet.utils;

import java.util.Collection;
import java.util.Map;

public final class ObjectUtils {
    private ObjectUtils() {
    }

    /**
     * Is null or empty boolean.
     *
     * @param object the object
     * @return the boolean
     */
    public static boolean isNullorEmpty(final Object object) {

        if (object instanceof String) {
            return object == null || ((String) object).trim().isEmpty();
        }

        if (object instanceof Collection<?>) {
            return object == null || ((Collection<?>) object).isEmpty();
        }

        if (object instanceof Map<?, ?>) {
            return object == null || ((Map<?, ?>) object).isEmpty();
        }

        return object == null;
    }

    /**
     * Is not null or empty boolean.
     *
     * @param object the object
     * @return the boolean
     */
    public static boolean isNotNullorEmpty(final Object object) {
        return !isNullorEmpty(object);
    }
}
