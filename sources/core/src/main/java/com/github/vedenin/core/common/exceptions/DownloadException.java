package com.github.vedenin.core.common.exceptions;

/**
 * Exception when program can't download this url
 * Created by Slava Vedenin on 6/6/2016.
 */
public class DownloadException extends RuntimeException {
    public DownloadException() {
    }

    public DownloadException(String message) {
        super(message);
    }

    public DownloadException(String message, Throwable cause) {
        super(message, cause);
    }

    public DownloadException(Throwable cause) {
        super(cause);
    }

    public DownloadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
