package crud.app.notesmanager.controllers;

import crud.app.notesmanager.dtos.AuthorRequest;
import crud.app.notesmanager.dtos.AuthorResponse;
import crud.app.notesmanager.services.AuthorService;
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
@RequestMapping("/api/authors")
@RequiredArgsConstructor
@Tag(name = "Authors", description = "Operations related to authors management")
public class AuthorController {
    private final AuthorService authorService;

    @Operation(summary = "Get all authors", description = "Retrieves all authors")
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "All existing authors are retrieved",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AuthorResponse.class))
                    )
            )
    )
    @GetMapping
    public Iterable<AuthorResponse> getAuthors() {
        return authorService.getAuthors();
    }

    @Operation(summary = "Create an author", description = "Creates an author")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Author created",
                    content = @Content(schema = @Schema(implementation = AuthorResponse.class)))}
    )
    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payload used to create an author",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = AuthorRequest.class))
            )
            @RequestBody AuthorRequest authorRequest,
            UriComponentsBuilder uriBuilder) {
        var createdAuthor = authorService.createAuthor(authorRequest);
        var uri =  uriBuilder.path("api/authors/{id}").buildAndExpand(createdAuthor.getId()).toUri();
        return ResponseEntity.created(uri).body(createdAuthor);
    }

    @Operation(summary = "Delete an existing author", description = "Deletes an existing author by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Author deleted"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAuthor(
            @Parameter(description = "ID of the author to delete", example = "5", required = true) @PathVariable Integer id) {
        var deletedAuthor = authorService.deleteAuthor(id);
        if (deletedAuthor){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get an author by ID", description = "Retrieves an author with an ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author found",
                    content = @Content(schema = @Schema(implementation = AuthorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Author not found")}
    )
    @GetMapping("{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(
            @Parameter(description = "ID of the author to retrieve", example = "5") @PathVariable Integer id) {
        var author = authorService.getAuthorById(id);
        if (author == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(author);
    }

    @Operation(summary = "Update an author by ID", description = "Updates an existing author with an ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author updated",
                    content = @Content(schema = @Schema(implementation = AuthorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Author not found")}
    )
    @PutMapping("{id}")
    public ResponseEntity<AuthorResponse> updateAuthor(
            @Parameter(description = "ID of the author to update", example = "5") @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payload used to update an existing author",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = AuthorRequest.class))
            )
            @RequestBody AuthorRequest authorRequest) {
        var updatedAuthor = authorService.updateAuthor(id, authorRequest);
        if (updatedAuthor == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(updatedAuthor);
    }
}
