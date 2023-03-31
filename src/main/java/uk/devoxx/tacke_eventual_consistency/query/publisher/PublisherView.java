package uk.devoxx.tacke_eventual_consistency.query.publisher;

import uk.devoxx.tacke_eventual_consistency.domaininteraction.publisher.PublisherDTO;

import java.util.UUID;

/**
 * @author Maik Kingma
 */

public record PublisherView(UUID id, String name) {
    public PublisherView(PublisherDTO publisherDTO) {
        this(publisherDTO.id(), publisherDTO.name());
    }
}
