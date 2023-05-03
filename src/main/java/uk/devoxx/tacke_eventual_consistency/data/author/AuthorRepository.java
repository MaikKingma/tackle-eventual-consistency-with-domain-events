package uk.devoxx.tacke_eventual_consistency.data.author;

import org.springframework.stereotype.Repository;
import uk.devoxx.tacke_eventual_consistency.data.custom.ExtendedRepository;

/**
 * @author Maik Kingma
 */

@Repository
public interface AuthorRepository extends ExtendedRepository<AuthorJPA, Long> {
}
