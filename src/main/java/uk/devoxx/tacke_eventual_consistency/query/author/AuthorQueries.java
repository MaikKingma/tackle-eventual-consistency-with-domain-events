package uk.devoxx.tacke_eventual_consistency.query.author;

import uk.devoxx.tacke_eventual_consistency.domaininteraction.author.AuthorFlow;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Maik Kingma
 */

@RestController
@RequestMapping(value = "/authors")
public class AuthorQueries {

    private final AuthorFlow authorFlow;

    public AuthorQueries(AuthorFlow authorFlow) {
        this.authorFlow = authorFlow;
    }

    @GetMapping
    public List<AuthorView> getAll() {
        return authorFlow.getListOfAllAuthors().stream()
                .map(AuthorView::new)
                .collect(toList());
    }
}
