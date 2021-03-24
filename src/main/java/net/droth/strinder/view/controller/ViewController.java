package net.droth.strinder.view.controller;

import lombok.extern.slf4j.Slf4j;
import net.droth.strinder.core.model.Configuration;
import net.droth.strinder.core.model.Genres;
import net.droth.strinder.core.model.Movies;
import net.droth.strinder.core.model.UserPair;
import net.droth.strinder.core.service.MovieService;
import net.droth.strinder.core.service.UserService;
import net.droth.strinder.view.model.UserType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Controller
public final class ViewController {

    private final MovieService movieService;
    private final UserService userService;

    public ViewController(final MovieService movieService, final UserService userService) {
        this.movieService = movieService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(final Model model) {

        Optional<Genres> optionalGenres = movieService.getGenres().blockOptional();
        List<Genres.Genre> genres = optionalGenres.map(Genres::getGenres).orElse(Collections.emptyList());
        model.addAttribute("genres", genres);

        UserPair userPair = userService.generateUserPair();
        model.addAttribute("userPair", userPair);
        model.addAttribute("userType", UserType.HOST);

        return "index";
    }

    @GetMapping("/u/{userId}")
    public String swipe(@PathVariable final UUID userId, final Model model) {

        if (!checkAndSetUser(userId, model)) {
            return "error";
        }

        Optional<Genres> optionalGenres = movieService.getGenres().blockOptional();
        List<Genres.Genre> genres = optionalGenres.map(Genres::getGenres).orElse(Collections.emptyList());
        model.addAttribute("genres", genres);

        return "index";
    }

    @GetMapping("/u/{userId}/g/{genreId}")
    public String genre(@PathVariable final UUID userId, @PathVariable final int genreId, final Model model) {

        if (!checkAndSetUser(userId, model)) {
            return "error";
        }

        Optional<Genres.Genre> selectedGenre = movieService.getGenres()
                .blockOptional()
                .flatMap(genres -> genres.getGenres().stream().filter(genre -> genre.getId() == genreId).findFirst());

        if (selectedGenre.isEmpty()) {
            log.warn("Genre '{}' does not exist", genreId);
            return "error";
        }

        model.addAttribute("genre", selectedGenre.get());

        Optional<Movies.Movie> randomMovie = movieService.getRandomMovie(genreId).blockOptional();
        if (randomMovie.isEmpty()) {
            log.error("No movie found for genre '{}'", genreId);
            return "error";
        }
        model.addAttribute("movie", randomMovie.get());

        Optional<Configuration> configuration = movieService.getConfiguration().blockOptional();
        if (configuration.isEmpty()) {
            log.error("Configuration could not be loaded");
            return "error";
        }

        Configuration.Images imgConf = configuration.get().getImages();
        String posterPath = imgConf.getBase_url() + imgConf.getPoster_sizes()[Math.min(2, imgConf.getPoster_sizes().length - 1)] + randomMovie.get().getPoster_path();
        model.addAttribute("posterPath", posterPath);

        return "swipe";
    }

    private boolean checkAndSetUser(final UUID userId, final Model model) {
        if (!userService.exists(userId)) {
            log.warn("User '{}' does not exist", userId);
            return false;
        }

        Optional<UserPair> userPair = userService.findUserPair(userId);
        if (userPair.isEmpty()) {
            log.warn("UserPair for '{}' does not exist", userId);
            return false;
        }

        model.addAttribute("userPair", userPair.get());
        model.addAttribute("userType", userType(userId, userPair.get()));
        return true;
    }

    private UserType userType(final UUID userId, final UserPair userPair) {
        if (userPair.getHost().getId().equals(userId)) {
            return UserType.HOST;
        } else if (userPair.getGuest().getId().equals(userId)) {
            return UserType.GUEST;
        }
        return UserType.UNKNOWN;
    }

}
