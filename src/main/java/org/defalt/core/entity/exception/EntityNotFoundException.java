package org.defalt.core.entity.exception;

import java.util.function.Supplier;

public class EntityNotFoundException extends RuntimeException {

    public static Supplier<EntityNotFoundException> supply(String message) {
        return () -> new EntityNotFoundException(message);
    }

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }

    public EntityNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
