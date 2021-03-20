package net.droth.strinder.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    private final ApiConfiguration apiConfiguration;

    public WebClientConfiguration(final ApiConfiguration apiConfiguration) {
        this.apiConfiguration = apiConfiguration;
    }

    @Bean
    public WebClient theMovieDbWebClient() {
        return WebClient.builder()
                .baseUrl(apiConfiguration.getTheMovieDb().getBaseUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiConfiguration.getTheMovieDb().getApiToken())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

}
