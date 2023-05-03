package uk.devoxx.tackle_eventual_consistency.domain.book;

import org.junit.jupiter.api.Test;
import uk.devoxx.tackle_eventual_consistency.domain.author.Author;


import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.devoxx.tackle_eventual_consistency.domain.book.Genre.HORROR;


class BookTest {

    @Test
    void requestPublishing() {
        // given
        Book book = Book.createManuscript("title", HORROR, Author.restore().firstName("first").lastName("last").id(1L).build());
        UUID publisherId = UUID.randomUUID();
        assertThat(book.isPublished()).isFalse();
        assertThat(book.getPublisherId()).isNull();
        assertThat(book.getIsbn()).isNull();
        assertThat(book.canBePublished()).isTrue();
        // when
        book.requestPublishing(publisherId);
        // then
        assertThat(book.isPublished()).isFalse();
        assertThat(book.getPublisherId()).isEqualTo(publisherId);
        assertThat(book.canBePublished()).isFalse();
        assertThat(book.getIsbn()).isNull();
        assertThat(book.getDomainEvents()).hasSize(1).containsExactly(new Book.RequestPublishingEvent(book.getId(), publisherId));
    }
}
