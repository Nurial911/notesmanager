package crud.app.notesmanager.dtos;

import lombok.Data;

@Data
public class AuthorResponse {
    private Integer id;
    private String name;
    private String email;
}
