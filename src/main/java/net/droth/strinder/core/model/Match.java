package net.droth.strinder.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public final class Match {

    private int movieId;
    private UserPair userPair;

}
