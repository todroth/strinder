package net.droth.strinder.core.exception;

import java.util.UUID;

public final class UserPairNotFoundException extends Exception {

    public UserPairNotFoundException(final UUID userId) {
        super(String.format("User Pair for '%s' not found", userId));
    }

}
