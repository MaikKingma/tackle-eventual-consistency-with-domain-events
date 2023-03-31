package uk.devoxx.tacke_eventual_consistency.data.book;

import uk.devoxx.tacke_eventual_consistency.data.author.AuthorJPAMapper;
import uk.devoxx.tacke_eventual_consistency.domaininteraction.book.BookDTO;

public class BookJPAMapper {
    static BookJPA mapToJPA(BookDTO bookDTO) {
        return BookJPA.builder()
                .id(bookDTO.id())
                .title(bookDTO.title())
                .author(AuthorJPAMapper.mapToJPA(bookDTO.authorDTO()))
                .genre(bookDTO.genre())
                .isbn(bookDTO.isbn())
                .published(bookDTO.published())
                .publisherId(bookDTO.publisherId())
                .build();
    }
}
