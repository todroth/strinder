package net.droth.strinder.core.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "api")
public class ApiConfiguration {

    private TheMovieDb theMovieDb;

    @Data
    public static final class TheMovieDb {
        private String baseUrl;
        private String apiToken;
    }

}
