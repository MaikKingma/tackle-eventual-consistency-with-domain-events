package uk.devoxx.tackle_eventual_consistency.query.book;

import uk.devoxx.tackle_eventual_consistency.domaininteraction.author.AuthorDTO;
import uk.devoxx.tackle_eventual_consistency.domaininteraction.author.AuthorFlow;
import uk.devoxx.tackle_eventual_consistency.domaininteraction.book.BookDTO;
import uk.devoxx.tackle_eventual_consistency.domaininteraction.book.BookFlow;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Maik Kingma
 */

@RestController
@RequestMapping(value = "/books")
public class BookQueries {
    private final BookFlow bookFlow;
    private final AuthorFlow authorFlow;

    public BookQueries(BookFlow bookFlow, AuthorFlow authorFlow) {
        this.bookFlow = bookFlow;
        this.authorFlow = authorFlow;
    }

    @GetMapping
    public List<BookView> findBooks(@RequestParam(value = "title", required = false) String title) {
        if(title == null || title.isEmpty()) {
            return bookFlow.findAllBooks().stream()
                    .map(bookDTO -> new BookView(bookDTO, getBookAuthor(bookDTO)))
                    .toList();
        } else {
            return bookFlow.findAllBooksWithMatchingTitle(title).stream()
                    .map(bookDTO -> new BookView(bookDTO, getBookAuthor(bookDTO)))
                    .toList();
        }
    }

    private AuthorDTO getBookAuthor(BookDTO bookDTO) {
        return authorFlow.findById(bookDTO.authorDTO().id());
    }
}
