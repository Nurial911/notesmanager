package crud.app.notesmanager.controllers;

import crud.app.notesmanager.dtos.CreateNoteRequest;
import crud.app.notesmanager.dtos.NoteResponse;
import crud.app.notesmanager.dtos.UpdateNoteRequest;
import crud.app.notesmanager.services.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@Tag(name = "Notes", description = "Operations related to notes management")
public class NoteController {
    private final NoteService noteService;

    @Operation(summary = "Get all notes", description = "Retrieves a list of all notes, optionally filtered by author ID")
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "All existing notes are retrieved",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = NoteResponse.class))
                    )
            )
    )
    @GetMapping
    public Iterable<NoteResponse> getAllNotes(@Parameter(description = "ID of the author", example = "5") @RequestParam(required = false, name = "authorId") Integer authorId) {
        if (authorId == null) {
            return noteService.getAllNotes();
        } else {
            return noteService.getAllNotesByAuthorId(authorId);
        }
    }

    @Operation(summary = "Get a note by ID", description = "Retrieves a note with an ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Note found", content = @Content(schema = @Schema(implementation = NoteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Note or Author not found")}
    )
    @GetMapping("{id}")
    public ResponseEntity<NoteResponse> getNoteById(@Parameter(description = "ID of the note to get", example = "67", required = true) @PathVariable Long id) {
        var note = noteService.getNoteById(id);
        if (note == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(note);
    }

    @Operation(summary = "Create a new note", description = "Creates a new note and associates it with an existing author")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Note created"),
            @ApiResponse(responseCode = "400", description = "Author not found")
    })
    @PostMapping
    public ResponseEntity<NoteResponse> createNote(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payload used to create a new note",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CreateNoteRequest.class)
                    )
            )
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

    @Operation(summary = "Update an existing note", description = "Updates the title, content, and author of an existing note by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Note updated"),
            @ApiResponse(responseCode = "404", description = "Note or Author not found")
    })
    @PutMapping("{id}")
    public ResponseEntity<NoteResponse> updateNote(
            @Parameter(description = "ID of the note to update", example = "5", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payload used to update an existing note",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UpdateNoteRequest.class))
            )
            @RequestBody UpdateNoteRequest updateNoteRequest
    ){
        var updatedNote = noteService.updateNote(updateNoteRequest, id);
        if (updatedNote == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(updatedNote);
    }

    @Operation(summary = "Delete an existing note", description = "Deletes an existing note by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Note deleted"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteNote(@Parameter(description = "ID of the note to delete", example = "5", required = true) @PathVariable Long id) {
        var deletedNote = noteService.deleteNote(id);
        if (deletedNote){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
