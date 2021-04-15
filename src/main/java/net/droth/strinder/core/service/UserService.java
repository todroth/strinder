package net.droth.strinder.core.service;

import net.droth.strinder.core.entity.UserEntity;
import net.droth.strinder.core.entity.UserPairEntity;
import net.droth.strinder.core.model.UserPair;
import net.droth.strinder.core.repository.UserPairRepository;
import net.droth.strinder.core.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserPairRepository userPairRepository;
    private final ModelMapper modelMapper;

    public UserService(final UserRepository userRepository, final UserPairRepository userPairRepository, final ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userPairRepository = userPairRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public UserPair generateUserPair() {
        UserEntity host = userRepository.save(new UserEntity());
        UserEntity guest = userRepository.save(new UserEntity());
        UserPairEntity userPair = userPairRepository.save(new UserPairEntity(host, guest));
        return modelMapper.map(userPair, UserPair.class);
    }

    @Transactional(readOnly = true)
    public Optional<UserPair> findUserPair(final UUID userId) {
        return userRepository.findById(userId)
                .flatMap(userPairRepository::findUserPairEntityByUser)
                .map(userPairEntity -> modelMapper.map(userPairEntity, UserPair.class));
    }

    @Transactional(readOnly = true)
    public boolean exists(final UUID userId) {
        return userRepository.existsById(userId);
    }

}
