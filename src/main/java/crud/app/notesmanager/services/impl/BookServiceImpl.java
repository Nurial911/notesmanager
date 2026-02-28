package crud.app.notesmanager.services.impl;

import crud.app.notesmanager.dtos.BookResponse;
import crud.app.notesmanager.dtos.CreateBookRequest;
import crud.app.notesmanager.dtos.UpdateBookRequest;
import crud.app.notesmanager.mappers.BookMapper;
import crud.app.notesmanager.repositories.BookRepository;
import crud.app.notesmanager.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::entityToDto) //same as book -> bookMapper.entityToDto(book)
                .toList();
    }

    @Override
    public BookResponse getBookById(Long id) {
        var book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return null;
        }
        return bookMapper.entityToDto(book);
    }

    @Override
    public BookResponse createBook(CreateBookRequest createBookRequest) {
        var bookEntity = bookMapper.dtoToEntity(createBookRequest);
        var savedBook = bookRepository.save(bookEntity);
        return bookMapper.entityToDto(savedBook);
    }

    @Override
    public BookResponse updateBookById(Long id, UpdateBookRequest updateBookRequest) {
        var bookToUpdate = bookRepository.findById(id).orElse(null);
        if (bookToUpdate == null) {
            return null;
        }
        bookMapper.updateBook(updateBookRequest, bookToUpdate);
        return bookMapper.entityToDto(bookRepository.save(bookToUpdate));
    }

    @Override
    public boolean deleteBookById(Long id) {
        var book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return false;
        }
        bookRepository.deleteById(id);
        return true;
    }
}
