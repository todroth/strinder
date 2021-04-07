package net.droth.strinder.core.service;

import net.droth.strinder.core.entity.SwipeEntity;
import net.droth.strinder.core.entity.UserEntity;
import net.droth.strinder.core.entity.UserPairEntity;
import net.droth.strinder.core.exception.UserNotFoundException;
import net.droth.strinder.core.exception.UserPairNotFoundException;
import net.droth.strinder.core.repository.SwipeRepository;
import net.droth.strinder.core.repository.UserPairRepository;
import net.droth.strinder.core.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class MatchService {

    private final UserRepository userRepository;
    private final SwipeRepository swipeRepository;
    private final UserPairRepository userPairRepository;

    public MatchService(final UserRepository userRepository, final SwipeRepository swipeRepository, final UserPairRepository userPairRepository) {
        this.userRepository = userRepository;
        this.swipeRepository = swipeRepository;
        this.userPairRepository = userPairRepository;
    }

    @Transactional(readOnly = true)
    public boolean hasMatch(final UUID userId, final int movieId) throws UserNotFoundException, UserPairNotFoundException {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Optional<SwipeEntity> swipeEntityOne = swipeRepository.findByUserIdAndMovieId(userId, movieId);

        UserPairEntity userPair = Stream.of(userPairRepository.findUserPairEntityByHost(user), userPairRepository.findUserPairEntityByGuest(user))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElseThrow(() -> new UserPairNotFoundException(userId));

        UUID userTwoId;
        if (userPair.getHost().getId().equals(userId)) {
            userTwoId = userPair.getGuest().getId();
        } else {
            userTwoId = userPair.getHost().getId();
        }
        Optional<SwipeEntity> swipeEntityTwo = swipeRepository.findByUserIdAndMovieId(userTwoId, movieId);

        return swipeEntityOne.isPresent() && swipeEntityOne.get().getSwipeType() == SwipeEntity.SwipeType.YES
                && swipeEntityTwo.isPresent() && swipeEntityTwo.get().getSwipeType() == SwipeEntity.SwipeType.YES;
    }

}
