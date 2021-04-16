package net.droth.strinder.core.service;

import net.droth.strinder.core.model.json.Configuration;
import net.droth.strinder.core.model.json.Genres;
import net.droth.strinder.core.model.json.Movies;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public final class TheMovieDbApi {

    private final WebClient webClient;

    public TheMovieDbApi(final WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Genres> getGenres() {
        return webClient.get()
                .uri("/genre/movie/list")
                .retrieve()
                .bodyToMono(Genres.class)
                .cache();
    }

    public Mono<Movies> getMovies(final int genre) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/discover/movie")
                        .queryParam("with_genres", genre)
                        .queryParam("sort_by", "popularity.desc")
                        .build())
                .retrieve()
                .bodyToMono(Movies.class)
                .cache();
    }

    public Mono<Configuration> getConfiguration() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/configuration").build())
                .retrieve()
                .bodyToMono(Configuration.class)
                .cache();
    }
}
