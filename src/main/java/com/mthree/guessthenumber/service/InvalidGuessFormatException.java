package com.mthree.guessthenumber.service;

/**
 *
 * @author john
 */
public class InvalidGuessFormatException extends Exception {

    public InvalidGuessFormatException(String message) {
        super(message);
    }

    public InvalidGuessFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
