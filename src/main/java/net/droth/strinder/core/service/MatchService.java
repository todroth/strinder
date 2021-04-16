package net.droth.strinder.core.service;

import net.droth.strinder.core.entity.UserEntity;
import net.droth.strinder.core.entity.UserPairEntity;
import net.droth.strinder.core.entity.UserPairEntity;
import net.droth.strinder.core.exception.UserNotFoundException;
import net.droth.strinder.core.exception.UserPairNotFoundException;
import net.droth.strinder.core.mapper.UserPairEntityMapper;
import net.droth.strinder.core.model.Match;
import net.droth.strinder.core.repository.SwipeRepository;
import net.droth.strinder.core.repository.UserPairRepository;
import net.droth.strinder.core.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final UserRepository userRepository;
    private final SwipeRepository swipeRepository;
    private final UserPairRepository userPairRepository;
    private final UserPairEntityMapper userPairEntityMapper;

    public MatchService(
            final UserRepository userRepository,
            final SwipeRepository swipeRepository,
            final UserPairRepository userPairRepository,
            final UserPairEntityMapper userPairEntityMapper
    ) {
        this.userRepository = userRepository;
        this.swipeRepository = swipeRepository;
        this.userPairRepository = userPairRepository;
        this.userPairEntityMapper = userPairEntityMapper;
    }

    @Transactional(readOnly = true)
    public List<Match> getMatches(final UUID userId) throws UserNotFoundException, UserPairNotFoundException {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        UserPairEntity userPairEntity = userPairRepository.findUserPairEntityByUser(userEntity).orElseThrow(() -> new UserPairNotFoundException(userId));
        List<Integer> matchingMovieIds = swipeRepository.findMatchingMovieIds(userPairEntity.getHost(), userPairEntity.getHost());
        return matchingMovieIds.stream().map(movieId -> new Match(movieId, userPairEntityMapper.map(userPairEntity))).collect(Collectors.toList());
    }

}
