package crud.app.notesmanager.mappers;

import crud.app.notesmanager.dtos.BookResponse;
import crud.app.notesmanager.dtos.CreateBookRequest;
import crud.app.notesmanager.dtos.UpdateBookRequest;
import crud.app.notesmanager.entities.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookResponse entityToDto(Book book);

    @Mapping(target = "id", ignore = true)
    Book dtoToEntity(CreateBookRequest createBookRequest);

    void updateBook(UpdateBookRequest bookToUpdate, @MappingTarget Book updatedBook);
}
