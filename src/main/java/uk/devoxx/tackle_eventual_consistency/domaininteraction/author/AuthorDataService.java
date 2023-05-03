package uk.devoxx.tackle_eventual_consistency.domaininteraction.author;

import java.util.List;

public interface AuthorDataService {
    void save(AuthorDTO author);

    List<AuthorDTO> findAll();

    AuthorDTO findById(Long authorId);

    class AuthorNotFoundException extends RuntimeException{
        public AuthorNotFoundException(String errorMessage) {
            super(errorMessage);
        }
    }
}
