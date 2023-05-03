package uk.devoxx.tackle_eventual_consistency.data.book;

import org.springframework.stereotype.Repository;
import uk.devoxx.tackle_eventual_consistency.data.baserepository.BaseJpaRepository;

import java.util.List;

/**
 * @author Maik Kingma
 */

@Repository
public interface BookRepository extends BaseJpaRepository<BookJPA, Long> {
    List<BookJPA> findByTitleContains(String title);
}
