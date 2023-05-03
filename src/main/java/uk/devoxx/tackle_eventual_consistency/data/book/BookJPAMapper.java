package uk.devoxx.tackle_eventual_consistency.data.book;

import uk.devoxx.tackle_eventual_consistency.data.author.AuthorJPAMapper;
import uk.devoxx.tackle_eventual_consistency.domaininteraction.book.BookDTO;

public class BookJPAMapper {
    static BookJPA mapToJPA(BookDTO bookDTO) {
        BookJPA bookJPA = BookJPA.builder()
                .id(bookDTO.id())
                .title(bookDTO.title())
                .author(AuthorJPAMapper.mapToJPA(bookDTO.authorDTO()))
                .genre(bookDTO.genre())
                .isbn(bookDTO.isbn())
                .published(bookDTO.published())
                .publisherId(bookDTO.publisherId())
                .build();
        bookJPA.registerDomainEvents(bookDTO.domainEvents());
        return bookJPA;
    }
}

