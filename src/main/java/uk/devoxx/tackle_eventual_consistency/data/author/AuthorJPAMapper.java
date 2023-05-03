package uk.devoxx.tackle_eventual_consistency.data.author;

import uk.devoxx.tackle_eventual_consistency.domaininteraction.author.AuthorDTO;

public class AuthorJPAMapper {
    public static AuthorJPA mapToJPA(AuthorDTO author){
        return AuthorJPA.builder()
                .id(author.id())
                .firstName(author.firstName())
                .lastName(author.lastName())
                .build();
    }
}
