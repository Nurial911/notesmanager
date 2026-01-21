package crud.app.notesmanager.services;

import crud.app.notesmanager.dtos.AuthorResponse;

public interface AuthorService {
    Iterable<AuthorResponse> getAuthors();
}
