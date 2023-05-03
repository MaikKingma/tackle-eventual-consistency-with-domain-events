package uk.devoxx.tackle_eventual_consistency.domaininteraction.author;

import uk.devoxx.tackle_eventual_consistency.domain.author.Author;
import uk.devoxx.tackle_eventual_consistency.domain.book.Genre;
import uk.devoxx.tackle_eventual_consistency.domaininteraction.book.BookDTO;
import uk.devoxx.tackle_eventual_consistency.domaininteraction.book.BookDataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorFlow {

    private final AuthorDataService authorDataService;
    private final BookDataService bookDataService;

    public AuthorFlow(AuthorDataService authorDataService, BookDataService bookDataService) {
        this.authorDataService = authorDataService;
        this.bookDataService = bookDataService;
    }

    public void registerAuthorByName(String firstName, String lastName) {
        Author author = Author.create(firstName, lastName);
        authorDataService.save(new AuthorDTO(author));
    }

    public List<AuthorDTO> getListOfAllAuthors() {
        return authorDataService.findAll();
    }

    public AuthorDTO findById(Long authorId) {
        return authorDataService.findById(authorId);
    }

    public void writeManuscript(Long authorId, String title, String genre) {
        var author = AuthorDomainMapper.mapToDomain(authorDataService.findById(authorId));
        var book = author.writeManuscript(title, Genre.fromString(genre));
        bookDataService.save(new BookDTO(book));
    }
}
