package uk.devoxx.tacke_eventual_consistency.domaininteraction.book;

import java.util.List;

/**
 * @author Maik Kingma
 */

public interface BookDataService {
    void save(BookDTO bookDTO);

    List<BookDTO> findAll();

    List<BookDTO> findByPartialTitle(String title);

    BookDTO findById(Long bookId);

    class BookNotFoundException  extends RuntimeException{
        public BookNotFoundException(String errorMessage) {
            super(errorMessage);
        }
    }
}
