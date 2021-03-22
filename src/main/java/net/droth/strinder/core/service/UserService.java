package net.droth.strinder.core.service;

import net.droth.strinder.core.entity.UserEntity;
import net.droth.strinder.core.entity.UserPairEntity;
import net.droth.strinder.core.model.UserPair;
import net.droth.strinder.core.repository.UserPairRepository;
import net.droth.strinder.core.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public final class UserService {

    private final UserRepository userRepository;
    private final UserPairRepository userPairRepository;
    private final ModelMapper modelMapper;

    public UserService(final UserRepository userRepository, final UserPairRepository userPairRepository, final ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userPairRepository = userPairRepository;
        this.modelMapper = modelMapper;
    }

    public UserPair generateUserPair() {
        UserEntity host = userRepository.save(new UserEntity());
        UserEntity guest = userRepository.save(new UserEntity());
        UserPairEntity userPair = userPairRepository.save(new UserPairEntity(host, guest));
        return modelMapper.map(userPair, UserPair.class);
    }

    public Optional<UserPair> findUserPair(final UUID userId) {
        return userRepository.findById(userId)
                .flatMap(userEntity -> Stream.of(
                        userPairRepository.findUserPairEntityByHost(userEntity),
                        userPairRepository.findUserPairEntityByGuest(userEntity)
                ).filter(Optional::isPresent)
                        .map(Optional::get)
                        .findFirst())
                .map(userPairEntity -> modelMapper.map(userPairEntity, UserPair.class));
    }

    public boolean exists(final UUID userId) {
        return userRepository.existsById(userId);
    }

}
