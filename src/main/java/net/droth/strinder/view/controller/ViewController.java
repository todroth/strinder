package net.droth.strinder.view.controller;

import lombok.extern.slf4j.Slf4j;
import net.droth.strinder.core.exception.*;
import net.droth.strinder.core.model.Configuration;
import net.droth.strinder.core.model.Genres;
import net.droth.strinder.core.model.Movies;
import net.droth.strinder.core.model.UserPair;
import net.droth.strinder.core.service.MovieService;
import net.droth.strinder.core.service.SwipeService;
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
    private final SwipeService swipeService;

    public ViewController(final MovieService movieService, final UserService userService, final SwipeService swipeService) {
        this.movieService = movieService;
        this.userService = userService;
        this.swipeService = swipeService;
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
    public String swipe(@PathVariable final UUID userId, final Model model)
            throws UserNotFoundException, UserPairNotFoundException {

        checkAndSetUser(userId, model);

        Optional<Genres> optionalGenres = movieService.getGenres().blockOptional();
        List<Genres.Genre> genres = optionalGenres.map(Genres::getGenres).orElse(Collections.emptyList());
        model.addAttribute("genres", genres);

        return "index";
    }

    @GetMapping("/u/{userId}/g/{genreId}")
    public String swipe(@PathVariable final UUID userId, @PathVariable final int genreId, final Model model)
            throws UserNotFoundException, UserPairNotFoundException, NoMovieFoundException, ConfigurationNotLoadedException, GenreNotFoundException {
        fillSwipePageData(userId, genreId, model);
        return "swipe";
    }

    @GetMapping("/u/{userId}/g/{genreId}/jop/{movieId}")
    public String swipeYes(@PathVariable final UUID userId, @PathVariable final int genreId, @PathVariable final int movieId, final Model model)
            throws UserNotFoundException, GenreNotFoundException, UserPairNotFoundException, NoMovieFoundException, ConfigurationNotLoadedException {

        fillSwipePageData(userId, genreId, model);

        swipeService.swipeYes(userId, movieId);

        String url = "/u/" + userId + "/g/" + genreId;
        return "redirect:" + url;

    }

    @GetMapping("/u/{userId}/g/{genreId}/nop/{movieId}")
    public String swipeNo(@PathVariable final UUID userId, @PathVariable final int genreId, @PathVariable final int movieId, final Model model)
            throws UserNotFoundException, GenreNotFoundException, UserPairNotFoundException, NoMovieFoundException, ConfigurationNotLoadedException {

        fillSwipePageData(userId, genreId, model);

        swipeService.swipeNo(userId, movieId);

        String url = "/u/" + userId + "/g/" + genreId;
        return "redirect:" + url;

    }

    private void fillSwipePageData(final UUID userId, final int genreId, final Model model)
            throws UserNotFoundException, GenreNotFoundException, NoMovieFoundException, ConfigurationNotLoadedException, UserPairNotFoundException {
        checkAndSetUser(userId, model);

        Genres.Genre selectedGenre = movieService.getGenres()
                .blockOptional()
                .flatMap(genres -> genres.getGenres().stream().filter(genre -> genre.getId() == genreId).findFirst())
                .orElseThrow(() -> new GenreNotFoundException(genreId));

        model.addAttribute("genre", selectedGenre);

        Movies.Movie randomMovie = movieService.getRandomMovie(genreId).blockOptional()
                .orElseThrow(() -> new NoMovieFoundException(genreId));

        model.addAttribute("movie", randomMovie);

        Configuration configuration = movieService.getConfiguration().blockOptional().orElseThrow(ConfigurationNotLoadedException::new);

        Configuration.Images imgConf = configuration.getImages();
        String posterPath = imgConf.getBase_url() + imgConf.getPoster_sizes()[Math.min(2, imgConf.getPoster_sizes().length - 1)] + randomMovie.getPoster_path();
        model.addAttribute("posterPath", posterPath);
    }

    private void checkAndSetUser(final UUID userId, final Model model)
            throws UserNotFoundException, UserPairNotFoundException {
        if (!userService.exists(userId)) {
            throw new UserNotFoundException(userId);
        }

        UserPair userPair = userService.findUserPair(userId).orElseThrow(() -> new UserPairNotFoundException(userId));

        model.addAttribute("userPair", userPair);
        model.addAttribute("userType", userType(userId, userPair));
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
