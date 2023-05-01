package uk.devoxx.tacke_eventual_consistency.domaininteraction.book;

import uk.devoxx.tacke_eventual_consistency.domain.book.Book;
import uk.devoxx.tacke_eventual_consistency.domain.book.Genre;
import uk.devoxx.tacke_eventual_consistency.domaininteraction.author.AuthorDomainMapper;

/**
 * @author Maik Kingma
 */

public class BookDomainMapper {
    public static Book mapToDomain(BookDTO bookDTO) {
        return Book.restore()
                .id(bookDTO.id())
                .title(bookDTO.title())
                .author(AuthorDomainMapper.mapToDomain(bookDTO.authorDTO()))
                .genre(Genre.fromString(bookDTO.genre()))
                .published(bookDTO.published())
                .publisherId(bookDTO.publisherId())
                .isbn(bookDTO.isbn())
                .build();
    }
}
