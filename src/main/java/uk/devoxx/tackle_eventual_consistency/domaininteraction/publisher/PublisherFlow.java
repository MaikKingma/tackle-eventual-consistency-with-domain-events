package uk.devoxx.tackle_eventual_consistency.domaininteraction.publisher;

import org.springframework.stereotype.Service;
import uk.devoxx.tackle_eventual_consistency.domaininteraction.book.BookDTO;
import uk.devoxx.tackle_eventual_consistency.domaininteraction.book.BookDataService;
import uk.devoxx.tackle_eventual_consistency.domaininteraction.book.BookDomainMapper;

import java.util.UUID;

/**
 * @author Maik Kingma
 */

@Service
public class PublisherFlow {

    private final PublisherAppService publisherAppService;
    private final BookDataService bookDataService;

    public PublisherFlow(PublisherAppService publisherAppService, BookDataService bookDataService) {
        this.publisherAppService = publisherAppService;
        this.bookDataService = bookDataService;
    }

    public void publishBook(Long bookId, String publisherId) {
        var publisherDTO = publisherAppService.getPublisherById(publisherId);
        var bookDTO = bookDataService.findById(bookId);
        var book = BookDomainMapper.mapToDomain(bookDTO);

        if (!book.canBePublished()) {
            throw new BookAlreadyInPublishingException("Book already in publishing!");
        }
        book.requestPublishing(publisherDTO.id());
        var updatedBookDto = new BookDTO(book);
        bookDataService.save(updatedBookDto);
    }

    public PublisherDTO findPublisherById(UUID publisherId) {
        return publisherAppService.getPublisherById(publisherId.toString());
    }

    static class BookAlreadyInPublishingException  extends RuntimeException{
        public BookAlreadyInPublishingException(String errorMessage) {
            super(errorMessage);
        }
    }
}
