package uk.devoxx.tacke_eventual_consistency.domaininteraction.publisher;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.devoxx.tacke_eventual_consistency.domaininteraction.author.AuthorDTO;
import uk.devoxx.tacke_eventual_consistency.domaininteraction.book.BookDTO;
import uk.devoxx.tacke_eventual_consistency.domaininteraction.book.BookDataService;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublisherFlowTest {

    @Mock
    private PublisherAppService publisherAppService;

    @Mock
    private BookDataService bookDataService;

    @InjectMocks
    private PublisherFlow publisherFlow;

    @Test
    void publishBook_success() {
        // given
        UUID publisherId = UUID.randomUUID();
        var authorDTO = new AuthorDTO(1L, "firstName", "lastName");
        var bookDTO = new BookDTO(1L, authorDTO, "title", "description", null, false, null);
        var publisherDTO = new PublisherDTO(publisherId,
                "publisherName");
        when(publisherAppService.getPublisherById(publisherId.toString())).thenReturn(publisherDTO);
        when(bookDataService.findById(1L)).thenReturn(bookDTO);
        // when
        publisherFlow.publishBook(1L, publisherId.toString());
        // then
        ArgumentCaptor<BookDTO> argumentCaptor = ArgumentCaptor.forClass(BookDTO.class);
        verify(bookDataService, times(1)).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().publisherId()).isEqualTo(publisherId);
    }

    @Test
    void publishBook_failure_publisherNotFound() {
        // given
        UUID publisherId = UUID.randomUUID();
        when(publisherAppService.getPublisherById(publisherId.toString())).thenThrow(
                new PublisherAppService.PublisherNotFoundException("Publisher not found!"));
        // when then
        assertThrows(PublisherAppService.PublisherNotFoundException.class, () -> publisherFlow.publishBook(1L,
                publisherId.toString()));
    }

    @Test
    void publishBook_failure_bookNotFound() {
        // given
        UUID publisherId = UUID.randomUUID();
        var publisherDTO = new PublisherDTO(publisherId,
                "publisherName");
        when(publisherAppService.getPublisherById(publisherId.toString())).thenReturn(publisherDTO);
        when(bookDataService.findById(1L)).thenThrow(new BookDataService.BookNotFoundException("Book not found!"));
        // when then
        assertThrows(BookDataService.BookNotFoundException.class, () -> publisherFlow.publishBook(1L,
                publisherId.toString()));
    }

    @Test
    void publishBook_failure_bookAlreadyInPublishing() {
        // given
        UUID publisherId = UUID.randomUUID();
        var authorDTO = new AuthorDTO(1L, "firstName", "lastName");
        var bookDTO = new BookDTO(1L, authorDTO, "title", "description", UUID.randomUUID(), false, null);
        var publisherDTO = new PublisherDTO(publisherId,
                "publisherName");
        when(publisherAppService.getPublisherById(publisherId.toString())).thenReturn(publisherDTO);
        when(bookDataService.findById(1L)).thenReturn(bookDTO);
        // when
        assertThrows(PublisherFlow.BookAlreadyInPublishingException.class, () -> publisherFlow.publishBook(1L,
                publisherId.toString()));
    }
}
