package net.droth.strinder.core.model;

import lombok.Data;

@Data
public final class UserPair {

    private User host;
    private User guest;

}
