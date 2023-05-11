package uk.devoxx.tackle_eventual_consistency.process.book;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uk.devoxx.tackle_eventual_consistency.domain.book.Book;
import uk.devoxx.tackle_eventual_consistency.domaininteraction.book.BookFlow;

/**
 * @author Maik Kingma
 */
@Service
public class PublishBookDelegate {
    private final BookFlow bookFlow;

    public PublishBookDelegate(BookFlow bookFlow) {
        this.bookFlow = bookFlow;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void publishBook(Book.RequestPublishingEvent event) {
        bookFlow.requestPublishingAtPublisher(event.getPublisherId(), event.getBookId());
    }
}
