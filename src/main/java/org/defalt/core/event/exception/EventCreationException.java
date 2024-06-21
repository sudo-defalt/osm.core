package org.defalt.core.event.exception;

public class EventCreationException extends RuntimeException{
    public EventCreationException() {
    }

    public EventCreationException(String message) {
        super(message);
    }

    public EventCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventCreationException(Throwable cause) {
        super(cause);
    }
}
