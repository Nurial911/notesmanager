package crud.app.notesmanager.services;

import crud.app.notesmanager.dtos.BookResponse;
import crud.app.notesmanager.dtos.CreateBookRequest;
import crud.app.notesmanager.dtos.UpdateBookRequest;

import java.util.List;

public interface BookService {
    List<BookResponse> getAllBooks();

    BookResponse getBookById(Long id);

    BookResponse createBook(CreateBookRequest createBookRequest);

    BookResponse updateBookById(Long id, UpdateBookRequest updateBookRequest);

    boolean deleteBookById(Long id);
}
