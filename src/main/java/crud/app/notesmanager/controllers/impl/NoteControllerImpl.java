package crud.app.notesmanager.controllers.impl;

import crud.app.notesmanager.controllers.NoteController;
import crud.app.notesmanager.dtos.CreateNoteRequest;
import crud.app.notesmanager.dtos.NoteResponse;
import crud.app.notesmanager.dtos.UpdateNoteRequest;
import crud.app.notesmanager.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class NoteControllerImpl implements NoteController {
    private final NoteService noteService;

    public Iterable<NoteResponse> getAllNotes(Integer authorId) {
        if (authorId == null) {
            return noteService.getAllNotes();
        } else {
            return noteService.getAllNotesByAuthorId(authorId);
        }
    }


    public ResponseEntity<NoteResponse> getNoteById(Long id) {
        var note = noteService.getNoteById(id);
        if (note == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(note);
    }


    public ResponseEntity<NoteResponse> createNote(
            CreateNoteRequest createNoteRequest,
            UriComponentsBuilder uriBuilder
    ) {
        var savedNote = noteService.createNote(createNoteRequest);
        if (savedNote == null) {
            return ResponseEntity.badRequest().build();
        }
        var uri = uriBuilder.path("/api/notes/{id}").buildAndExpand(savedNote.getId()).toUri();
        return ResponseEntity.created(uri).body(savedNote);
    }


    public ResponseEntity<NoteResponse> updateNote(
            Long id,
            UpdateNoteRequest updateNoteRequest
    ){
        var updatedNote = noteService.updateNote(updateNoteRequest, id);
        if (updatedNote == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(updatedNote);
    }


    public ResponseEntity<Void> deleteNote(Long id) {
        var deletedNote = noteService.deleteNote(id);
        if (deletedNote){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
