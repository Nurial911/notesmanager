package crud.app.notesmanager.dtos;

import lombok.Data;

@Data
public class UpdateNoteRequest {
    private String title;
    private String content;
    private Integer authorId;
}
