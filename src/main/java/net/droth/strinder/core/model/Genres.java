package net.droth.strinder.core.model;

import lombok.Data;

import java.util.List;

@Data
public final class Genres {

    private List<Genre> genres;

    @Data
    public static final class Genre {
        private int id;
        private String name;
    }

}
