package net.droth.strinder.core.exception;

public final class NoMovieFoundException extends Exception {

    public NoMovieFoundException(final int genreId) {
        super(String.format("No movie found for genre '%d'", genreId));
    }

}
