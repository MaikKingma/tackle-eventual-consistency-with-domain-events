package uk.devoxx.tackle_eventual_consistency.query.author;

import uk.devoxx.tackle_eventual_consistency.domaininteraction.author.AuthorDTO;

/**
 * @author Maik Kingma
 */

public record AuthorView (Long id, String name) {
    public AuthorView(AuthorDTO authorDTO) {
        this(authorDTO.id(), authorDTO.getFullName());
    }
}
