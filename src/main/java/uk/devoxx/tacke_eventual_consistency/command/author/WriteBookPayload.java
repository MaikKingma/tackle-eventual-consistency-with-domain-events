package uk.devoxx.tacke_eventual_consistency.command.author;

/**
 * @author Maik Kingma
 */

public record WriteBookPayload(String title, String genre) {
}
