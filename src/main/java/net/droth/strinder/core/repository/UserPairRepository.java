package net.droth.strinder.core.repository;

import net.droth.strinder.core.entity.UserEntity;
import net.droth.strinder.core.entity.UserPairEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserPairRepository extends CrudRepository<UserPairEntity, Integer> {

    @Query("SELECT u FROM UserPair u WHERE u.guest = :user OR u.host = :user")
    Optional<UserPairEntity> findUserPairEntityByUser(@Param("user") final UserEntity user);

}
