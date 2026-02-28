package crud.app.notesmanager.dtos;

import lombok.Data;

@Data
public class BookResponse {
    private Long id;
    private String title;
    private String description;
}
