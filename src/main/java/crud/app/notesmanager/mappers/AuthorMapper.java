package crud.app.notesmanager.mappers;

import crud.app.notesmanager.dtos.AuthorRequest;
import crud.app.notesmanager.dtos.AuthorResponse;
import crud.app.notesmanager.entities.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorResponse entityToDto(Author author);

    @Mapping(target = "id", ignore = true)
    Author dtoToEntity(AuthorRequest authorRequest);
}
