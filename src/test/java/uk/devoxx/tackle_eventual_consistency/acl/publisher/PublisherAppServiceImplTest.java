package uk.devoxx.tackle_eventual_consistency.acl.publisher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import uk.devoxx.tackle_eventual_consistency.domaininteraction.publisher.PublisherDTO;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PublisherAppServiceImplTest {

    private PublisherAppServiceImpl publisherAppService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        publisherAppService = new PublisherAppServiceImpl();
        ReflectionTestUtils.setField(publisherAppService, "publisherServiceBaseUri", "http://localhost:8080");
        ReflectionTestUtils.setField(publisherAppService, "restTemplate", restTemplate);
    }


    @Test
    public void testGetAllPublishers() {
        UUID firstPublisherId = UUID.randomUUID();
        UUID secondPublisherId = UUID.randomUUID();
        PublisherAppServiceImpl.PublisherPayload[] publisherPayloads = {
                new PublisherAppServiceImpl.PublisherPayload(firstPublisherId, "Publisher 1"),
                new PublisherAppServiceImpl.PublisherPayload(secondPublisherId, "Publisher 2"),
        };

        when(restTemplate.getForObject(anyString(), any())).thenReturn(publisherPayloads);

        List<PublisherDTO> publisherDTOs = publisherAppService.getAllPublishers();

        assertThat(publisherDTOs).containsExactlyInAnyOrder(new PublisherDTO(firstPublisherId, "Publisher 1"),
                new PublisherDTO(secondPublisherId, "Publisher 2"));
    }

    @Test
    public void testGetPublisherById() {
        UUID publisherId = UUID.randomUUID();
        PublisherAppServiceImpl.PublisherPayload publisherPayload =
                new PublisherAppServiceImpl.PublisherPayload(publisherId, "Publisher 1");

        when(restTemplate.getForObject(anyString(), any())).thenReturn(publisherPayload);

        PublisherDTO publisherDTO = publisherAppService.getPublisherById(publisherId.toString());

        assertThat(publisherDTO.id()).isEqualTo(publisherId);
        assertThat(publisherDTO.name()).isEqualTo("Publisher 1");
    }

    @Test
    public void testRequestPublishing() {
        UUID publisherId = UUID.randomUUID();
        String isbn = "1234567890123";
        PublisherAppServiceImpl.RequestPublishingResponsePayload responsePayload =
                new PublisherAppServiceImpl.RequestPublishingResponsePayload(isbn);

        when(restTemplate.postForObject(anyString(), any(), any())).thenReturn(responsePayload);

        String resultIsbn = publisherAppService.requestPublishing(publisherId, "Author Name", "Book Title");

        assertThat(resultIsbn).isEqualTo(isbn);
    }
}
