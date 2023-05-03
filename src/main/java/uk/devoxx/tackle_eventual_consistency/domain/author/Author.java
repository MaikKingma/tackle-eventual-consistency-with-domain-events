package uk.devoxx.tackle_eventual_consistency.domain.author;

import uk.devoxx.tackle_eventual_consistency.domain.book.Book;
import uk.devoxx.tackle_eventual_consistency.domain.book.Genre;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


/**
 * The Author domain aggregate
 */

@Builder(builderMethodName = "restore")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Author {
    @Getter
    private Long id;
    @Getter
    private String firstName;
    @Getter
    private String lastName;

    /**
     * Public factory method for an {@link  Author} Aggregate. This method is essentially part of the Domain interaction layer
     * but resides withing the aggregate itself due to the private access modifier of the all args constructor.
     *
     * @param firstName the first name of the author
     * @param lastName the last name of the author
     * @return the {@link  Author}
     */
    public static Author create(String firstName, String lastName) {
        return new Author(null, firstName, lastName);
    }

    public Book writeManuscript(String title, Genre genre) {
        return Book.createManuscript(title, genre, this);
    }
}
