package net.droth.strinder.core.exception;

import java.util.UUID;

public final class UserNotFoundException extends Exception {

    public UserNotFoundException(final UUID userId) {
        super(String.format("User '%s' does not exist", userId));
    }

}
