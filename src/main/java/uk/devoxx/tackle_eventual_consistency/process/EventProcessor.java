package uk.devoxx.tackle_eventual_consistency.process;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import uk.devoxx.tackle_eventual_consistency.domain.book.Book;
import uk.devoxx.tackle_eventual_consistency.process.book.PublishBookDelegate;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

/**
 * @author Maik Kingma
 */

@Slf4j
@Component
public class EventProcessor {

    private final PublishBookDelegate publishBookDelegate;

    public EventProcessor(PublishBookDelegate publishBookDelegate) {
        this.publishBookDelegate = publishBookDelegate;
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void handleEvent(Book.RequestPublishingEvent requestPublishingEvent) {
        log.info(requestPublishingEvent.toString());
        publishBookDelegate.publishBook(requestPublishingEvent);
    }
}
