package net.droth.strinder.core.repository;

import net.droth.strinder.core.entity.SwipeEntity;
import net.droth.strinder.core.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SwipeRepository extends CrudRepository<SwipeEntity, Integer> {

    Optional<SwipeEntity> findByUserIdAndMovieId(final UUID userId, final int movieId);

    @Query("SELECT swipe.movieId from Swipe swipe WHERE (swipe.user = :userOne OR swipe.user = :userTwo) AND swipe.swipeType = net.droth.strinder.core.entity.SwipeEntity$SwipeType.YES")
    List<Integer> findMatchingMovieIds(@Param("userOne") final UserEntity userOne, @Param("userTwo") final UserEntity userTwo);

}
