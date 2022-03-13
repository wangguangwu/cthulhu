package com.wangguangwu.client.exception;

/**
 * Exception thrown with the response code start with 40, such as 400
 *
 * @author wangguangwu
 */
@SuppressWarnings("unused")
public class BadResponseException extends RuntimeException {


    /**
     * construction method
     */
    public BadResponseException() {
    }

    /**
     * construction method
     *
     * @param message message
     */
    public BadResponseException(String message) {
        super(message);
    }


    /**
     * construction method
     *
     * @param message message
     * @param cause   cause
     */
    public BadResponseException(String message, Throwable cause) {
        super(message, cause);
    }


    /**
     * construction method
     *
     * @param cause cause
     */
    public BadResponseException(Throwable cause) {
        super(cause);
    }


    /**
     * construction method
     *
     * @param message            message
     * @param cause              cause
     * @param enableSuppression  enableSuppression
     * @param writableStackTrace writableStackTrace
     */
    public BadResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
