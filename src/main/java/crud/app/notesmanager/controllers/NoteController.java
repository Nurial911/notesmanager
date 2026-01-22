package crud.app.notesmanager.controllers;

import crud.app.notesmanager.dtos.CreateNoteRequest;
import crud.app.notesmanager.dtos.NoteResponse;
import crud.app.notesmanager.dtos.UpdateNoteRequest;
import crud.app.notesmanager.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @GetMapping
    public Iterable<NoteResponse> getAllNotes() {
        return noteService.getAllNotes();
    }

    @GetMapping("{id}")
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable Long id) {
        var note = noteService.getNoteById(id);
        if (note == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(note);
    }

    @PostMapping
    public ResponseEntity<NoteResponse> createNote(
            @RequestBody CreateNoteRequest createNoteRequest,
            UriComponentsBuilder uriBuilder
    ) {
        var savedNote = noteService.createNote(createNoteRequest);
        if (savedNote == null) {
            return ResponseEntity.badRequest().build();
        }
        var uri = uriBuilder.path("/api/notes/{id}").buildAndExpand(savedNote.getId()).toUri();
        return ResponseEntity.created(uri).body(savedNote);
    }

    @PutMapping("{id}")
    public ResponseEntity<NoteResponse> updateNote(
            @PathVariable Long id,
            @RequestBody UpdateNoteRequest updateNoteRequest
    ){
        var updatedNote = noteService.updateNote(updateNoteRequest, id);
        if (updatedNote == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(updatedNote);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        var deletedNote = noteService.deleteNote(id);
        if (deletedNote){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
