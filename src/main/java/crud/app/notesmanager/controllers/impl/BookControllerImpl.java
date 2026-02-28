package crud.app.notesmanager.controllers.impl;

import crud.app.notesmanager.controllers.BookController;
import crud.app.notesmanager.dtos.BookResponse;
import crud.app.notesmanager.dtos.CreateBookRequest;
import crud.app.notesmanager.dtos.UpdateBookRequest;
import crud.app.notesmanager.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookControllerImpl implements BookController {
    private final BookService bookService;

    @Override
    public ResponseEntity<BookResponse> createBook(CreateBookRequest createBookRequest, UriComponentsBuilder uriBuilder) {
        var createdBook = bookService.createBook(createBookRequest);
        var uri = uriBuilder.path("api/books/{id}").buildAndExpand(createdBook.getId()).toUri();
        return ResponseEntity.created(uri).body(createdBook);
    }

    @Override
    public ResponseEntity<BookResponse> getBookById(Long id) {
        var book = bookService.getBookById(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(book);
    }

    @Override
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        var books = bookService.getAllBooks();
        return ResponseEntity.ok().body(books);
    }

    @Override
    public ResponseEntity<BookResponse> updateBook(Long id, UpdateBookRequest updateBookRequest) {
        var updatedBook = bookService.updateBookById(id, updateBookRequest);
        if (updatedBook == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(updatedBook);
    }

    @Override
    public ResponseEntity<Void> deleteBook(Long id) {
        if(bookService.deleteBookById(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
