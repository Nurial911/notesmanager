package crud.app.notesmanager.services;

import crud.app.notesmanager.dtos.NoteResponse;

public interface NoteService {
    public Iterable<NoteResponse> getAllNotes();

    public NoteResponse getNoteById(Long id);
}
