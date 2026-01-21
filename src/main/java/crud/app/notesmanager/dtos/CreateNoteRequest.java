package crud.app.notesmanager.dtos;

import lombok.Data;

@Data
public class CreateNoteRequest {
    private String title;
    private String content;
}
