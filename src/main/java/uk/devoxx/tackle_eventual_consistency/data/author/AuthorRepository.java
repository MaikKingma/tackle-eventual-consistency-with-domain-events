package uk.devoxx.tackle_eventual_consistency.data.author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Maik Kingma
 */

@Repository
public interface AuthorRepository extends JpaRepository<AuthorJPA, Long> {
}
