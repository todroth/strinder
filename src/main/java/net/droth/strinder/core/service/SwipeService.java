package net.droth.strinder.core.service;

import lombok.extern.slf4j.Slf4j;
import net.droth.strinder.core.entity.SwipeEntity;
import net.droth.strinder.core.entity.UserEntity;
import net.droth.strinder.core.repository.SwipeRepository;
import net.droth.strinder.core.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class SwipeService {

    private final UserRepository userRepository;
    private final SwipeRepository swipeRepository;

    public SwipeService(final UserRepository userRepository, final SwipeRepository swipeRepository) {
        this.userRepository = userRepository;
        this.swipeRepository = swipeRepository;
    }

    @Transactional
    public void swipeYes(final UUID userId, final int movieId) {
        swipe(userId, movieId, SwipeEntity.SwipeType.YES);
        log.debug("User '{}' swiped YES for '{}'", userId, movieId);
    }

    @Transactional
    public void swipeNo(final UUID userId, final int movieId) {
        swipe(userId, movieId, SwipeEntity.SwipeType.NO);
        log.debug("User '{}' swiped NO for '{}'", userId, movieId);
    }

    private void swipe(final UUID userId, final int movieId, final SwipeEntity.SwipeType swipeType) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            log.error("User '{}' does not exist", userId);
            return;
        }

        Optional<SwipeEntity> swipeEntity = swipeRepository.findByUserIdAndMovieId(userId, movieId);
        swipeEntity.ifPresent(s -> s.setSwipeType(swipeType));
        swipeRepository.save(swipeEntity.orElse(new SwipeEntity(user.get(), movieId, swipeType)));
    }
}
