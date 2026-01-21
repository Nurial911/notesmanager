package crud.app.notesmanager.services;

import crud.app.notesmanager.dtos.CreateNoteRequest;
import crud.app.notesmanager.dtos.NoteResponse;
import crud.app.notesmanager.dtos.UpdateNoteRequest;

public interface NoteService {
    public Iterable<NoteResponse> getAllNotes();

    public NoteResponse getNoteById(Long id);

    public NoteResponse createNote(CreateNoteRequest createNoteRequest);

    public NoteResponse updateNote(UpdateNoteRequest updateNoteRequest, Long id);
}
