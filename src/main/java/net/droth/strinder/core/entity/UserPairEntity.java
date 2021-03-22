package net.droth.strinder.core.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Data
@Entity(name = "UserPair")
public final class UserPairEntity implements Serializable {

    public UserPairEntity() {
    }

    public UserPairEntity(final UserEntity host, final UserEntity guest) {
        this.host = host;
        this.guest = guest;
    }

    @Id
    private int id;

    @OneToOne
    private UserEntity host;

    @OneToOne
    private UserEntity guest;

}
