package crud.app.notesmanager.controllers.impl;

import crud.app.notesmanager.controllers.AuthorController;
import crud.app.notesmanager.dtos.AuthorRequest;
import crud.app.notesmanager.dtos.AuthorResponse;
import crud.app.notesmanager.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class AuthorControllerImpl implements AuthorController {
    private final AuthorService authorService;

    public Iterable<AuthorResponse> getAuthors() {
        return authorService.getAuthors();
    }

    public ResponseEntity<AuthorResponse> createAuthor(
            AuthorRequest authorRequest,
            UriComponentsBuilder uriBuilder) {
        var createdAuthor = authorService.createAuthor(authorRequest);
        var uri =  uriBuilder.path("api/authors/{id}").buildAndExpand(createdAuthor.getId()).toUri();
        return ResponseEntity.created(uri).body(createdAuthor);
    }

    public ResponseEntity<Void> deleteAuthor(Integer id) {
        var deletedAuthor = authorService.deleteAuthor(id);
        if (deletedAuthor){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<AuthorResponse> getAuthorById(Integer id) {
        var author = authorService.getAuthorById(id);
        if (author == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(author);
    }

    public ResponseEntity<AuthorResponse> updateAuthor(Integer id, AuthorRequest authorRequest) {
        var updatedAuthor = authorService.updateAuthor(id, authorRequest);
        if (updatedAuthor == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(updatedAuthor);
    }
}
