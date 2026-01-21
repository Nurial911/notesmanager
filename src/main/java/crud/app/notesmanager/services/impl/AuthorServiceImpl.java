package crud.app.notesmanager.services.impl;

import crud.app.notesmanager.dtos.AuthorRequest;
import crud.app.notesmanager.dtos.AuthorResponse;
import crud.app.notesmanager.entities.Author;
import crud.app.notesmanager.mappers.AuthorMapper;
import crud.app.notesmanager.repositories.AuthorRepository;
import crud.app.notesmanager.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public Iterable<AuthorResponse> getAuthors(){
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::entityToDto)
                .toList();
    }

    public AuthorResponse createAuthor(AuthorRequest authorRequest) {
        var author = authorMapper.dtoToEntity(authorRequest);
        var savedAuthor = authorRepository.save(author);
        return authorMapper.entityToDto(savedAuthor);
    }

    public boolean deleteAuthor(Integer id) {
        var author =  authorRepository.findById(id).orElse(null);
        if(author == null){
            return false;
        }
        authorRepository.delete(author);
        return true;
    }
}
