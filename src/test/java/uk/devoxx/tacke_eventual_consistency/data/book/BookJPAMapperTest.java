package uk.devoxx.tacke_eventual_consistency.data.book;

import uk.devoxx.tacke_eventual_consistency.data.author.AuthorJPAMapper;
import uk.devoxx.tacke_eventual_consistency.domaininteraction.author.AuthorDTO;
import uk.devoxx.tacke_eventual_consistency.domaininteraction.book.BookDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BookJPAMapperTest {

    @Test
    void mapToJPA() {
        AuthorDTO authorDTO = new AuthorDTO(1L, "firstName", "lastName");
        BookJPA bookJPA = BookJPAMapper.mapToJPA(new BookDTO(1L, authorDTO, "title", "genre",
                null, false, null));
        assertThat(bookJPA).usingRecursiveComparison().isEqualTo(BookJPA.builder()
                .id(1L)
                .title("title")
                .author(AuthorJPAMapper.mapToJPA(authorDTO))
                .genre("genre")
                .isbn(null)
                .published(false)
                .publisherId(null)
                .build());
    }
}