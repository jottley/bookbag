package net.ottleys.bookbag.services;

public class UnknownContentPathException extends Exception {

    private static final long serialVersionUID = 1L;

    public UnknownContentPathException() {
        super("Content path cannot be found.");
    }

    public UnknownContentPathException(String path) {
        super("Content path cannot be found.: " + path);
    }
}