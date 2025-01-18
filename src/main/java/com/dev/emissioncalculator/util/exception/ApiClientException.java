package com.dev.emissioncalculator.util.exception;

/**
 * Custom exception class for handling API-client related exceptions.
 */
public class ApiClientException extends RuntimeException{

    /**
     * Constructor to create an ApiClientException with a custom error message
     *
     * @param message the detail message specifying the reason for exception
     */
    public ApiClientException(String message) {
        super(message);
    }

    /**
     * Constructor to create an ApiClientException with a custom error message and a cause
     *
     * @param message the detail message specifying the reason for exception
     * @param cause the throwable cause that trigger this exception
     */

    public ApiClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
