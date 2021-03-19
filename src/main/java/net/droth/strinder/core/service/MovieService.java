package net.droth.strinder.core.service;

import net.droth.strinder.core.model.Movie;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class MovieService {

    private final MovieApi movieApi;

    public MovieService(final MovieApi movieApi) {
        this.movieApi = movieApi;
    }

    public Flux<Movie> getMovies() {
        return movieApi.fetchMovies();
    }

}
