package net.droth.strinder.core.repository;

import net.droth.strinder.core.entity.SwipeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface SwipeRepository extends CrudRepository<SwipeEntity, Integer> {

    Optional<SwipeEntity> findByUserIdAndMovieId(final UUID userId, final int movieId);

}
