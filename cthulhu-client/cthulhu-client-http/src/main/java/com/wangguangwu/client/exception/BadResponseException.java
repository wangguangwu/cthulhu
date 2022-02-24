package com.wangguangwu.client.exception;

/**
 * Exception thrown with the response code start with 40, such as 400
 *
 * @author wangguangwu
 * @date 2022/2/24
 */
public class BadResponseException extends RuntimeException {

    public BadResponseException() {
    }

    public BadResponseException(String message) {
        super(message);
    }

    public BadResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadResponseException(Throwable cause) {
        super(cause);
    }

    public BadResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
