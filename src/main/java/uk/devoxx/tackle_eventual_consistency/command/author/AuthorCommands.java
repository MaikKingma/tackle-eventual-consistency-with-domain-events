package uk.devoxx.tackle_eventual_consistency.command.author;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.devoxx.tackle_eventual_consistency.domaininteraction.author.AuthorFlow;

@RestController
@RequestMapping("authors/commands")
public class AuthorCommands {

    private final AuthorFlow authorFlow;

    public AuthorCommands(AuthorFlow authorFlow) {
        this.authorFlow = authorFlow;
    }

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void create(@RequestBody RegisterAuthorPayload registerAuthorPayload) {
        authorFlow.registerAuthorByName(registerAuthorPayload.firstName(), registerAuthorPayload.lastName());
    }
}
