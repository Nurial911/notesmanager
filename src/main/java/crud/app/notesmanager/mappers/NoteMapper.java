package crud.app.notesmanager.mappers;

import crud.app.notesmanager.dtos.NoteResponse;
import crud.app.notesmanager.entities.Note;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteMapper {
    NoteResponse entityToDto(Note note);
}
