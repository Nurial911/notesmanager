package crud.app.notesmanager.dtos;

import lombok.Data;

@Data
public class UpdateBookRequest {
    private String title;
    private String description;
}
