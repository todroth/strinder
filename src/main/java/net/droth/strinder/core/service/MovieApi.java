package net.droth.strinder.core.service;

import net.droth.strinder.core.model.Movie;
import reactor.core.publisher.Flux;

public interface MovieApi {

    Flux<Movie> fetchMovies();

}
