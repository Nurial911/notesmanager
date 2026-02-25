package crud.app.notesmanager.controllers;

import crud.app.notesmanager.dtos.AuthorRequest;
import crud.app.notesmanager.dtos.AuthorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "Authors", description = "Operations related to authors management")
@RequestMapping("/api/authors")
public interface AuthorController {
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
    public Iterable<AuthorResponse> getAuthors();

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
            ) @RequestBody AuthorRequest authorRequest, UriComponentsBuilder uriBuilder);

    @Operation(summary = "Delete an existing author", description = "Deletes an existing author by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Author deleted"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAuthor(@Parameter(description = "ID of the author to delete", example = "5", required = true)
                                                 @PathVariable Integer id);

    @Operation(summary = "Get an author by ID", description = "Retrieves an author with an ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author found",
                    content = @Content(schema = @Schema(implementation = AuthorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Author not found")}
    )
    @GetMapping("{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(@Parameter(description = "ID of the author to retrieve", example = "5")
                                                            @PathVariable Integer id);

    @Operation(summary = "Update an author by ID", description = "Updates an existing author with an ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author updated",
                    content = @Content(schema = @Schema(implementation = AuthorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Author not found")}
    )
    @PutMapping("{id}")
    public ResponseEntity<AuthorResponse> updateAuthor(@Parameter(description = "ID of the author to update", example = "5") @PathVariable Integer id,
                                                       @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                               description = "Payload used to update an existing author",
                                                               required = true,
                                                               content = @Content(
                                                                       schema = @Schema(implementation = AuthorRequest.class))
                                                       ) @RequestBody AuthorRequest authorRequest);
}
