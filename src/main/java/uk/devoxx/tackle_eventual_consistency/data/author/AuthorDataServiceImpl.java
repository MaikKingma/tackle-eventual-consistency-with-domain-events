package uk.devoxx.tackle_eventual_consistency.data.author;

import uk.devoxx.tackle_eventual_consistency.domaininteraction.author.AuthorDTO;
import uk.devoxx.tackle_eventual_consistency.domaininteraction.author.AuthorDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AuthorDataServiceImpl implements AuthorDataService {

    private final AuthorRepository authorRepository;

    public AuthorDataServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void save(AuthorDTO authorDTO) {
        log.info("registering authorDTO {}", authorDTO.getFullName());
        authorRepository.save(AuthorJPAMapper.mapToJPA(authorDTO));
    }

    @Override
    public List<AuthorDTO> findAll() {
        return authorRepository.findAll().stream()
                .map(this::getDTOFromJPA)
                .toList();
    }

    @Override
    public AuthorDTO findById(Long authorId) {
        return authorRepository.findById(authorId)
                .map(this::getDTOFromJPA)
                .orElseThrow(() -> new AuthorNotFoundException(String.format("Author with id %d could not be found!", authorId)));
    }

    private AuthorDTO getDTOFromJPA(AuthorJPA authorJPA) {
        return new AuthorDTO(authorJPA.getId(), authorJPA.getFirstName(), authorJPA.getLastName());
    }
}
