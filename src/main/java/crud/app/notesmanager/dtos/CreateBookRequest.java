package crud.app.notesmanager.dtos;

import lombok.Data;

@Data
public class CreateBookRequest {
    private String title;
    private String description;
}
