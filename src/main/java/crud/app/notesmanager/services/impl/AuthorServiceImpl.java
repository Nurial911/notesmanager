package crud.app.notesmanager.services.impl;

import crud.app.notesmanager.repositories.AuthorRepository;
import crud.app.notesmanager.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
}
