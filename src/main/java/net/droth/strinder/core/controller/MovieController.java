package net.droth.strinder.core.controller;

import net.droth.strinder.core.model.Genres;
import net.droth.strinder.core.model.Movies;
import net.droth.strinder.core.service.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public final class MovieController {

    private final MovieService movieService;

    public MovieController(final MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/genres")
    public Mono<Genres> getGenres() {
        return movieService.getGenres();
    }

    @GetMapping("/movies/{genre}")
    public Mono<Movies> getMovies(@PathVariable final int genre) {
        return movieService.getMovies(genre);
    }

}
