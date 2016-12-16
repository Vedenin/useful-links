package com.github.vedenin.core.common.exceptions;

/**
 * RuntimeException if resources can't be open
 *
 * Created by vedenin on 04.06.16.
 */
public class StoreException extends RuntimeException {
    public StoreException() {
    }

    public StoreException(String message) {
        super(message);
    }

    public StoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public StoreException(Throwable cause) {
        super(cause);
    }

    public StoreException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
