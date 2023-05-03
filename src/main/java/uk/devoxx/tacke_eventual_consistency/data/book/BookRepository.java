package uk.devoxx.tacke_eventual_consistency.data.book;

import org.springframework.stereotype.Repository;
import uk.devoxx.tacke_eventual_consistency.data.custom.ExtendedRepository;

import java.util.List;

/**
 * @author Maik Kingma
 */

@Repository
public interface BookRepository extends ExtendedRepository<BookJPA, Long> {
    List<BookJPA> findByTitleContains(String title);
}
