package crud.app.notesmanager.mappers;

import crud.app.notesmanager.dtos.CreateNoteRequest;
import crud.app.notesmanager.dtos.NoteResponse;
import crud.app.notesmanager.entities.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NoteMapper {
    NoteResponse entityToDto(Note note);

    @Mapping(target = "id", ignore = true) // id is generated
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Note dtoToEntity(CreateNoteRequest createNoteRequest);
}
