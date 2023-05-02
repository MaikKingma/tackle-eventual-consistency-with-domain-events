package uk.devoxx.tacke_eventual_consistency.query.book;

import uk.devoxx.tacke_eventual_consistency.domaininteraction.author.AuthorDTO;
import uk.devoxx.tacke_eventual_consistency.domaininteraction.author.AuthorFlow;
import uk.devoxx.tacke_eventual_consistency.domaininteraction.book.BookDTO;
import uk.devoxx.tacke_eventual_consistency.domaininteraction.book.BookFlow;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.devoxx.tacke_eventual_consistency.domaininteraction.publisher.PublisherFlow;

import java.util.List;
import java.util.UUID;

/**
 * @author Maik Kingma
 */

@RestController
@RequestMapping(value = "/books")
public class BookQueries {
    private final BookFlow bookFlow;
    private final AuthorFlow authorFlow;
    private final PublisherFlow publisherFlow;

    public BookQueries(BookFlow bookFlow, AuthorFlow authorFlow, PublisherFlow publisherFlow) {
        this.bookFlow = bookFlow;
        this.authorFlow = authorFlow;
        this.publisherFlow = publisherFlow;
    }

    @GetMapping
    public List<BookView> findBooks(@RequestParam(value = "title", required = false) String title) {
        if(title == null || title.isEmpty()) {
            return bookFlow.findAllBooks().stream()
                    .map(bookDTO -> new BookView(bookDTO, getBookAuthor(bookDTO), getPublisherName(bookDTO.publisherId())))
                    .toList();
        } else {
            return bookFlow.findAllBooksWithMatchingTitle(title).stream()
                    .map(bookDTO -> new BookView(bookDTO, getBookAuthor(bookDTO), getPublisherName(bookDTO.publisherId())))
                    .toList();
        }
    }

    private String getPublisherName(UUID publisherId) {
        if (publisherId != null) {
            try {
                return publisherFlow.findPublisherById(publisherId).name();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    private AuthorDTO getBookAuthor(BookDTO bookDTO) {
        return authorFlow.findById(bookDTO.authorDTO().id());
    }
}
