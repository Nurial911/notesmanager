package crud.app.notesmanager.services;

import crud.app.notesmanager.dtos.AuthorRequest;
import crud.app.notesmanager.dtos.AuthorResponse;

public interface AuthorService {
    Iterable<AuthorResponse> getAuthors();

    AuthorResponse createAuthor(AuthorRequest authorRequest);

    boolean deleteAuthor(Integer id);

    AuthorResponse getAuthorById(Integer id);

    AuthorResponse updateAuthor(Integer id, AuthorRequest authorRequest);
}
