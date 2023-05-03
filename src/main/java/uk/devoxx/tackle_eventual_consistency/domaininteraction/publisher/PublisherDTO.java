package uk.devoxx.tackle_eventual_consistency.domaininteraction.publisher;

import uk.devoxx.tackle_eventual_consistency.domain.publisher.Publisher;

import java.util.UUID;

/**
 * @author Maik Kingma
 */

public record PublisherDTO(UUID id, String name) {
    public PublisherDTO(Publisher publisher) {
        this(publisher.id(), publisher.name());
    }
}
