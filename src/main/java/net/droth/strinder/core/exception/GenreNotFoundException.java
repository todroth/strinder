package net.droth.strinder.core.exception;

public final class GenreNotFoundException extends Exception {

    public GenreNotFoundException(final int genreId) {
        super(String.format("Genre '%d' does not exist", genreId));
    }

}
