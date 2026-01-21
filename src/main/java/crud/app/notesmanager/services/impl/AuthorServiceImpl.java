package crud.app.notesmanager.services.impl;

import crud.app.notesmanager.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl {
    private final AuthorRepository authorRepository;
}
