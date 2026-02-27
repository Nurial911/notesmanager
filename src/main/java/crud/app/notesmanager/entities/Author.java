package crud.app.notesmanager.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "authors")
@Getter
@Setter
public class Author {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;
}
