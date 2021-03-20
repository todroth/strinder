package net.droth.strinder.core.service;

import net.droth.strinder.core.model.Genres;
import net.droth.strinder.core.model.Movies;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public final class MovieService {

    private final TheMovieDbApi theMovieDbApi;

    public MovieService(final TheMovieDbApi theMovieDbApi) {
        this.theMovieDbApi = theMovieDbApi;
    }

    public Mono<Genres> getGenres() {
        return theMovieDbApi.getGenres();
    }

    public Mono<Movies> getMovies(final int genre) {
        return theMovieDbApi.getMovies(genre);
    }
}
