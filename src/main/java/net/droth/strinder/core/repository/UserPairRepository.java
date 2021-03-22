package net.droth.strinder.core.repository;

import net.droth.strinder.core.entity.UserEntity;
import net.droth.strinder.core.entity.UserPairEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserPairRepository extends CrudRepository<UserPairEntity, Integer> {

    Optional<UserPairEntity> findUserPairEntityByHost(final UserEntity host);
    Optional<UserPairEntity> findUserPairEntityByGuest(final UserEntity guest);

}
