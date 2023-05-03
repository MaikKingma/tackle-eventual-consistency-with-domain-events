package uk.devoxx.tackle_eventual_consistency.data.author;

import org.springframework.stereotype.Repository;
import uk.devoxx.tackle_eventual_consistency.data.baserepository.BaseJpaRepository;

/**
 * @author Maik Kingma
 */

@Repository
public interface AuthorRepository extends BaseJpaRepository<AuthorJPA, Long> {
}
