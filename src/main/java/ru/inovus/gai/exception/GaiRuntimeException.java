package ru.inovus.gai.exception;

public class GaiRuntimeException extends RuntimeException {

    public GaiRuntimeException(String message) {
        super(message);
    }

    public GaiRuntimeException(String msgTemplate, Object[] args) {
        super(String.format(msgTemplate, args));
    }

    public GaiRuntimeException(String message, Exception e) {
        super(message, e);
    }
}
