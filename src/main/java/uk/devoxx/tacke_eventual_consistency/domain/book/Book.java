package uk.devoxx.tacke_eventual_consistency.domain.book;

import uk.devoxx.tacke_eventual_consistency.domain.author.Author;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

/**
 * @author Maik Kingma
 */

@Builder(builderMethodName = "restore")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Book {

    @Getter
    private Long id;

    @Getter
    private Author author;

    @Getter
    private String title;

    @Getter
    private Genre genre;

    @Getter
    private UUID publisherId;

    @Getter
    private boolean published;

    @Getter
    private String isbn;

    public static Book createManuscript(String title, Genre genre, Author author) {
        return new Book(null, author, title, genre, null, false, null);
    }

    public void requestPublishing(UUID publisherId) {
        this.publisherId = publisherId;
    }

    public boolean canBePublished() {
        return publisherId == null && !published;
    }
}
