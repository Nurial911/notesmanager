package crud.app.notesmanager.controllers;

import crud.app.notesmanager.dtos.NoteResponse;
import crud.app.notesmanager.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
