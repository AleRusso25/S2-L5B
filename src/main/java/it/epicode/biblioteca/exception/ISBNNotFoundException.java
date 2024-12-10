package it.epicode.biblioteca.exception;


public class ISBNNotFoundException extends RuntimeException {
    public ISBNNotFoundException(String message) {
        super(message);
    }
}
