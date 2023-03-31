package uk.devoxx.tacke_eventual_consistency.query.publisher;

import uk.devoxx.tacke_eventual_consistency.domaininteraction.publisher.PublisherAppService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Maik Kingma
 */

@RestController
@RequestMapping(value = "/publishers")
public class PublisherQuery {

    private final PublisherAppService publisherAppService;

    public PublisherQuery(PublisherAppService publisherAppService) {
        this.publisherAppService = publisherAppService;
    }

    @GetMapping
    List<PublisherView> getAllPublishers() {
        return publisherAppService.getAllPublishers().stream()
                .map(PublisherView::new)
                .toList();
    }
}
