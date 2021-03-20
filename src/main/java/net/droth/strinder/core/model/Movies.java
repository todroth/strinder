package net.droth.strinder.core.model;

import lombok.Data;

import java.util.List;

@Data
public final class Movies {

    private int page;
    private List<Movie> results;
    private int total_results;
    private int total_pages;

    @Data
    public static final class Movie {
        private String poster_path;
        private boolean adult;
        private String overview;
        private String release_date;
        private List<Integer> genre_ids;
        private int id;
        private String original_title;
        private String original_language;
        private String title;
        private String backdrop_path;
        private double popularity;
        private int vote_count;
        private boolean video;
        private double vote_average;
    }

}
