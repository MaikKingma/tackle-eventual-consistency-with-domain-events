package uk.devoxx.tacke_eventual_consistency.data.author;

import uk.devoxx.tacke_eventual_consistency.domaininteraction.author.AuthorDTO;

public class AuthorJPAMapper {
    public static AuthorJPA mapToJPA(AuthorDTO author){
        return AuthorJPA.builder()
                .id(author.id())
                .firstName(author.firstName())
                .lastName(author.lastName())
                .build();
    }
}
