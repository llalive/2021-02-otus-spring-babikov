package dev.lochness.tickets.exceptions;

public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String message) {
        super(message);
    }

    public InvalidRequestException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
