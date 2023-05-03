package uk.devoxx.tackle_eventual_consistency.domain.book;

import java.util.Arrays;

/**
 * @author Maik Kingma
 */

public enum Genre {
    FANTASY("fantasy"),
    CRIME("crime"),
    ROMANCE("romance"),
    HORROR("horror"),
    OTHER("other");

    private final String value;

    Genre(String value) {
        this.value = value;
    }

    public static Genre fromString(String input) {
        return Arrays.stream(Genre.values())
                .filter(genre -> genre.value.equalsIgnoreCase(input))
                .findFirst().orElse(OTHER);
    }
}
