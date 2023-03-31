package uk.devoxx.tacke_eventual_consistency.domaininteraction.publisher;

import java.util.List;

/**
 * @author Maik Kingma
 */

public interface PublisherAppService {
    List<PublisherDTO> getAllPublishers();
}
