package uk.devoxx.tacke_eventual_consistency.query.book;

import uk.devoxx.tacke_eventual_consistency.domaininteraction.author.AuthorDTO;
import uk.devoxx.tacke_eventual_consistency.domaininteraction.book.BookDTO;

/**
 * @author Maik Kingma
 */

public record BookView(Long id, String title, String genre, String authorName) {
    public BookView(BookDTO bookDTO, AuthorDTO authorDTO) {
        this(bookDTO.id(), bookDTO.title(), bookDTO.genre(), authorDTO.getFullName());
    }
}
