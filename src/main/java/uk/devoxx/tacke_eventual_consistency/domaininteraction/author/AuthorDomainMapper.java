package uk.devoxx.tacke_eventual_consistency.domaininteraction.author;

import uk.devoxx.tacke_eventual_consistency.domain.author.Author;

class AuthorDomainMapper {
    // we keep this method because we will need it later when actual business logic needs to be triggered from the
    // domain core
    static Author mapToDomain(AuthorDTO authorJPA) {
        return Author.restore()
                .id(authorJPA.id())
                .firstName(authorJPA.firstName())
                .lastName(authorJPA.lastName())
                .build();
    }
}
