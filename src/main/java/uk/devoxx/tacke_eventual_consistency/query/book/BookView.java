package uk.devoxx.tacke_eventual_consistency.query.book;

import uk.devoxx.tacke_eventual_consistency.domaininteraction.author.AuthorDTO;
import uk.devoxx.tacke_eventual_consistency.domaininteraction.book.BookDTO;

/**
 * @author Maik Kingma
 */

public record BookView(Long id, String title, String genre, String isbn, String publisherName, String authorName) {
    public BookView(BookDTO bookDTO, AuthorDTO authorDTO, String publisherName) {
        this(bookDTO.id(), bookDTO.title(), bookDTO.genre(), bookDTO.isbn(), publisherName, authorDTO.getFullName());
    }
}
