package crud.app.notesmanager.controllers;

import crud.app.notesmanager.dtos.AuthorRequest;
import crud.app.notesmanager.dtos.AuthorResponse;
import crud.app.notesmanager.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    public Iterable<AuthorResponse> getAuthors() {
        return authorService.getAuthors();
    }

    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@RequestBody AuthorRequest authorRequest, UriComponentsBuilder uriBuilder) {
        var createdAuthor = authorService.createAuthor(authorRequest);
        var uri =  uriBuilder.path("/authors/{id}").buildAndExpand(createdAuthor.getId()).toUri();
        return ResponseEntity.created(uri).body(createdAuthor);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Integer id) {
        var deletedAuthor = authorService.deleteAuthor(id);
        if (deletedAuthor){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable Integer id) {
        var author = authorService.getAuthorById(id);
        if (author == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(author);
    }

    @PutMapping("{id}")
    public ResponseEntity<AuthorResponse> updateAuthor(@PathVariable Integer id, @RequestBody AuthorRequest authorRequest) {
        var updatedAuthor = authorService.updateAuthor(id, authorRequest);
        if (updatedAuthor == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(updatedAuthor);
    }
}
