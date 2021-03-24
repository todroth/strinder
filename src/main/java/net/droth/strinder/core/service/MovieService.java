package net.droth.strinder.core.service;

import net.droth.strinder.core.model.Configuration;
import net.droth.strinder.core.model.Genres;
import net.droth.strinder.core.model.Movies;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Random;

@Service
public final class MovieService {

    private final TheMovieDbApi theMovieDbApi;

    private static final Random random = new Random();

    public MovieService(final TheMovieDbApi theMovieDbApi) {
        this.theMovieDbApi = theMovieDbApi;
    }

    public Mono<Genres> getGenres() {
        return theMovieDbApi.getGenres();
    }

    public Mono<Movies> getMovies(final int genre) {
        return theMovieDbApi.getMovies(genre);
    }

    // TODO somehow load more movies and do not only show the first page of results
    public Mono<Movies.Movie> getRandomMovie(final int genre) {
        return getMovies(genre).map(Movies::getResults).map(results -> results.get(random.nextInt(results.size() - 1)));
    }

    public Mono<Configuration> getConfiguration() {
        return theMovieDbApi.getConfiguration();
    }
}
