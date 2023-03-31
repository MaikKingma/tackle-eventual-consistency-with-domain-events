package uk.devoxx.tacke_eventual_consistency.domaininteraction.book;

import uk.devoxx.tacke_eventual_consistency.domain.book.Book;
import uk.devoxx.tacke_eventual_consistency.domaininteraction.author.AuthorDTO;

import java.util.UUID;

/**
 * @author Maik Kingma
 */

public record BookDTO(Long id, AuthorDTO authorDTO, String title, String genre, UUID publisherId, boolean published,
                      String isbn) {
    public BookDTO(Book book) {
        this(book.getId(), new AuthorDTO(book.getAuthor()), book.getTitle(), book.getGenre().toString(),
                book.getPublisherId(), book.isPublished(), book.getIsbn());
    }
}
