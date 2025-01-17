package com.dev.emissionCalculator.util.exception;

/**
 * Custom exception class for handling invalid input scenarios.
 */
public class InvalidInputException extends RuntimeException {
    /**
     * Constructor to create an InvalidInputException with a custom error message
     *
     * @param message the detail message specifying the reason for exception
     */
    public InvalidInputException(String message) {
        super(message);
    }

    /**
     * Constructor to create an InvalidInputException with a custom error message and a cause
     *
     * @param message the detail message specifying the reason for exception
     * @param cause the throwable cause that trigger this exception
     */

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
