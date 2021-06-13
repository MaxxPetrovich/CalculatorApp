package ru.mpetrov.calculatorapp.exceptions;

public class TooBigException extends Exception {
    public TooBigException() {
    }

    public TooBigException(String message) {
        super(message);
    }

    public TooBigException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooBigException(Throwable cause) {
        super(cause);
    }
}
