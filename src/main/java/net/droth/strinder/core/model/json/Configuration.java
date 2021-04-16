package net.droth.strinder.core.model.json;

import lombok.Data;

@Data
public final class Configuration {

    private Images images;
    private String[] change_keys;

    @Data
    public static final class Images {
        private String base_url;
        private String secure_base_url;
        private String[] backdrop_sizes;
        private String[] logo_sizes;
        private String[] poster_sizes;
        private String[] profile_sizes;
        private String[] still_sizes;
    }
}
