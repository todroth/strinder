package net.droth.strinder.core.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity(name = "Swipe")
public final class SwipeEntity {

    public SwipeEntity() {
    }

    public SwipeEntity(final UserEntity user, final int movieId, final SwipeType swipeType) {
        this.user = user;
        this.movieId = movieId;
        this.swipeType = swipeType;
    }

    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    private UserEntity user;

    private int movieId;

    private SwipeType swipeType;

    public enum SwipeType {
        YES, NO
    }

}
