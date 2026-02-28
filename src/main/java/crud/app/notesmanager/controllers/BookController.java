package crud.app.notesmanager.controllers;

import crud.app.notesmanager.dtos.BookResponse;
import crud.app.notesmanager.dtos.CreateBookRequest;
import crud.app.notesmanager.dtos.UpdateBookRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RequestMapping("/api/books")
public interface BookController {
    @PostMapping
    ResponseEntity<BookResponse> createBook(@RequestBody CreateBookRequest createBookRequest, UriComponentsBuilder uriBuilder);

    @GetMapping("/{id}")
    ResponseEntity<BookResponse> getBookById(@PathVariable Long id);

    @GetMapping
    ResponseEntity<List<BookResponse>> getAllBooks();

    @PutMapping("/{id}")
    ResponseEntity<BookResponse> updateBook(@PathVariable Long id, @RequestBody UpdateBookRequest updateBookRequest);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteBook(@PathVariable Long id);
}
