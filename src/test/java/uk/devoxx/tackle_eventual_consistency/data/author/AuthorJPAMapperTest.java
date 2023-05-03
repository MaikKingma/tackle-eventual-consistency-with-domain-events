package uk.devoxx.tackle_eventual_consistency.data.author;

import uk.devoxx.tackle_eventual_consistency.domaininteraction.author.AuthorDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorJPAMapperTest {

    @Test
    void mapToJPA() {
        // given
        AuthorDTO input = new AuthorDTO(1L, "first", "last");
        AuthorJPA expectedOutput = AuthorJPA.builder()
                .id(1L)
                .firstName("first")
                .lastName("last")
                .build();
        // when
        AuthorJPA result = AuthorJPAMapper.mapToJPA(input);
        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedOutput);
    }
}
