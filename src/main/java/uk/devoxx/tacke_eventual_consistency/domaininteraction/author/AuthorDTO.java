package uk.devoxx.tacke_eventual_consistency.domaininteraction.author;

import uk.devoxx.tacke_eventual_consistency.domain.author.Author;

public record AuthorDTO(Long id, String firstName, String lastName) {
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public AuthorDTO(Author author) {
        this(author.getId(), author.getFirstName(), author.getLastName());
    }
}
