package net.droth.strinder.core.service;

import net.droth.strinder.core.configuration.ApiConfiguration;
import net.droth.strinder.core.model.Movie;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class TheMovieDbAdapter implements MovieApi {

    private final ApiConfiguration apiConfiguration;

    public TheMovieDbAdapter(final ApiConfiguration apiConfiguration) {
        this.apiConfiguration = apiConfiguration;
    }

    @Override
    public Flux<Movie> fetchMovies() {
        return Flux.fromArray(new Movie[]{
                new Movie(apiConfiguration.getTheMovieDb().getApiKey())
        });
    }

}
