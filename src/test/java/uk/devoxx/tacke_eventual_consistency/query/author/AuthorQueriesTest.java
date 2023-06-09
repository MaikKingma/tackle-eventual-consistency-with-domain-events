package uk.devoxx.tacke_eventual_consistency.query.author;

import uk.devoxx.tacke_eventual_consistency.domain.author.Author;
import uk.devoxx.tacke_eventual_consistency.domaininteraction.author.AuthorDTO;
import uk.devoxx.tacke_eventual_consistency.domaininteraction.author.AuthorFlow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorQueriesTest {

    @Mock
    private AuthorFlow authorFlow;

    @InjectMocks
    private AuthorQueries authorQueries;

    @Test
    void getAll() {
        // given
        List<AuthorDTO> mockedAuthorListResponse =
                List.of(new AuthorDTO(Author.restore().id(1L).firstName("firstName").lastName("lastName").build()));
        when(authorFlow.getListOfAllAuthors()).thenReturn(mockedAuthorListResponse);
        // when then
        List<AuthorView> result = authorQueries.getAll();
        verify(authorFlow, times(1)).getListOfAllAuthors();
        assertThat(result).containsExactly(new AuthorView(1L, "firstName lastName"));
    }
}