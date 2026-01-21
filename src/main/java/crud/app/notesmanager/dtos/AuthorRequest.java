package crud.app.notesmanager.dtos;

import lombok.Data;

@Data
public class AuthorRequest {
    private String name;
    private String email;
}
