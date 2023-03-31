package uk.devoxx.tacke_eventual_consistency.query.author;

import uk.devoxx.tacke_eventual_consistency.domaininteraction.author.AuthorDTO;

/**
 * @author Maik Kingma
 */

public record AuthorView (Long id, String name) {
    public AuthorView(AuthorDTO authorDTO) {
        this(authorDTO.id(), authorDTO.getFullName());
    }
}
