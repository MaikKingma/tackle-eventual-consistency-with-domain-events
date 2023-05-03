package uk.devoxx.tackle_eventual_consistency.query.book;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.Header;
import org.mockserver.springtest.MockServerTest;
import uk.devoxx.tackle_eventual_consistency.data.author.AuthorJPA;
import uk.devoxx.tackle_eventual_consistency.data.book.BookJPA;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.json.Json;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockServerTest
@SpringBootTest
@AutoConfigureMockMvc
class BookQueriesIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager entityManager;

    private MockServerClient mockServerClient;

    @BeforeEach
    void beforeEach() {
        entityManager.createNativeQuery("DELETE FROM author where true; DELETE FROM book where true;")
                .executeUpdate();
    }

    @Test
    @Transactional
    void shouldFindBooksWithNoQueryParam() throws Exception {
        // given
        UUID publisherUUID = UUID.randomUUID();
        configureMockGetPublisherById(publisherUUID.toString());
        entityManager.createNativeQuery(
                        "INSERT INTO author (id, first_name, last_name) VALUES (?,?,?)")
                .setParameter(1, 1)
                .setParameter(2, "firstName")
                .setParameter(3, "lastName")
                .executeUpdate();
        var book1 = BookJPA.builder()
                .author(AuthorJPA.builder().id(1L).build())
                .genre("HORROR")
                .title("horror-book")
                .build();
        var book2 = BookJPA.builder()
                .author(AuthorJPA.builder().id(1L).build())
                .genre("ROMANCE")
                .title("romance-book")
                .publisherId(publisherUUID)
                .published(true)
                .isbn("123456")
                .build();
        var expectedBookView1 = new BookView(1L, "horror-book", "HORROR", null, null, "firstName lastName");
        var expectedBookView2 = new BookView(2L, "romance-book", "ROMANCE", "123456", "the/experts", "firstName lastName");

        entityManager.persist(book1);
        entityManager.persist(book2);
        entityManager.flush();
        // when
        MvcResult result = mockMvc.perform(get("/books")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        // then
        var resultingBookViews = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<BookView>>() {
                });
        assertThat(resultingBookViews).hasSize(2);
        assertThat(resultingBookViews).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactlyInAnyOrder(expectedBookView1, expectedBookView2);
    }

    @Test
    @Transactional
    void shouldFindBooksFilteredByQueryParamTitle() throws Exception {
        // given
        entityManager.createNativeQuery(
                        "INSERT INTO author (id, first_name, last_name) VALUES (?,?,?)")
                .setParameter(1, 1)
                .setParameter(2, "firstName")
                .setParameter(3, "lastName")
                .executeUpdate();
        var book1 = BookJPA.builder()
                .author(AuthorJPA.builder().id(1L).build())
                .genre("HORROR")
                .title("horror-book")
                .build();
        var book2 = BookJPA.builder()
                .author(AuthorJPA.builder().id(1L).build())
                .genre("ROMANCE")
                .title("romance-book")
                .build();
        var expectedBookView = new BookView(1L, "horror-book", "HORROR", null, null, "firstName lastName");

        entityManager.persist(book1);
        entityManager.persist(book2);
        entityManager.flush();
        // when
        MvcResult result = mockMvc.perform(get("/books")
                        .accept(MediaType.APPLICATION_JSON)
                        .queryParam("title", "orror-"))
                .andExpect(status().isOk())
                .andReturn();
        // then
        var resultingBookViews = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<BookView>>() { });
        assertThat(resultingBookViews).hasSize(1);
        assertThat(resultingBookViews).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactly(expectedBookView);
    }

    private void configureMockGetPublisherById(String publisherId) {
        var responseBody = Json.createObjectBuilder()
                .add("id", publisherId)
                .add("name", "the/experts")
                .add("taxNumber", "VAT12345")
                .add("numberOfEmployees", 30)
                .add("yearlyRevenueInMillions", 99)
                .add("amountOfBooksPublished", 20)
                .build().toString();

        mockServerClient.when(request().withMethod("GET").withPath("/publishers/" + publisherId), exactly(1)).respond(
                response()
                        .withStatusCode(200)
                        .withHeaders(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(responseBody)
                        .withDelay(TimeUnit.SECONDS,1)
        );
    }
}
