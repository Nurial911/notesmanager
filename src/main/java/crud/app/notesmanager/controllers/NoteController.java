package crud.app.notesmanager.controllers;

import crud.app.notesmanager.dtos.CreateNoteRequest;
import crud.app.notesmanager.dtos.NoteResponse;
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
        var uri = uriBuilder.path("/api/notes/{id}").buildAndExpand(savedNote.getId()).toUri();
        return ResponseEntity.created(uri).body(savedNote);
    }
}
