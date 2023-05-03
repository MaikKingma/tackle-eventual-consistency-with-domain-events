package uk.devoxx.tackle_eventual_consistency.domaininteraction.author;

import uk.devoxx.tackle_eventual_consistency.domain.author.Author;

public record AuthorDTO(Long id, String firstName, String lastName) {
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public AuthorDTO(Author author) {
        this(author.getId(), author.getFirstName(), author.getLastName());
    }
}
