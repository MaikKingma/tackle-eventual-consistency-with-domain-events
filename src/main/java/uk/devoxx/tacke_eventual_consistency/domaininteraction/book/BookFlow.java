package uk.devoxx.tacke_eventual_consistency.domaininteraction.book;

import org.springframework.stereotype.Service;
import uk.devoxx.tacke_eventual_consistency.domaininteraction.publisher.PublisherAppService;

import java.util.List;
import java.util.UUID;

/**
 * @author Maik Kingma
 */

@Service
public class BookFlow {
    private final BookDataService bookDataService;
    private final PublisherAppService publisherAppService;

    public BookFlow(BookDataService bookDataService, PublisherAppService publisherAppService) {
        this.bookDataService = bookDataService;
        this.publisherAppService = publisherAppService;
    }

    public List<BookDTO> findAllBooks() {
        return bookDataService.findAll();
    }

    public List<BookDTO> findAllBooksWithMatchingTitle(String title) {
        return bookDataService.findByPartialTitle(title);
    }

    public void requestPublishingAtPublisher(UUID publisherId, Long bookId) {
        var bookDTO = bookDataService.findById(bookId);
        var isbn = publisherAppService.requestPublishing(
                publisherId,
                bookDTO.authorDTO().getFullName(),
                bookDTO.title());

        var book = BookDomainMapper.mapToDomain(bookDTO);
        book.updatePublishingInfo(isbn);
        var updatedBookDTO = new BookDTO(book);

        bookDataService.save(updatedBookDTO);
    }
}
