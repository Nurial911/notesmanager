package crud.app.notesmanager.services;

import crud.app.notesmanager.dtos.CreateNoteRequest;
import crud.app.notesmanager.dtos.NoteResponse;
import crud.app.notesmanager.dtos.UpdateNoteRequest;

public interface NoteService {
    Iterable<NoteResponse> getAllNotes();

    NoteResponse getNoteById(Long id);

    NoteResponse createNote(CreateNoteRequest createNoteRequest);

    NoteResponse updateNote(UpdateNoteRequest updateNoteRequest, Long id);

    boolean deleteNote(Long id);
}
