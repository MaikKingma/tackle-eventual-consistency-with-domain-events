package uk.devoxx.tackle_eventual_consistency.domain.publisher;

import java.util.UUID;

/**
 * @author Maik Kingma
 */

public record Publisher(UUID id, String name) {
}
