package net.droth.strinder.core.exception;

public final class ConfigurationNotLoadedException extends Exception {

    public ConfigurationNotLoadedException() {
        super("Configuration could not be loaded");
    }

}
