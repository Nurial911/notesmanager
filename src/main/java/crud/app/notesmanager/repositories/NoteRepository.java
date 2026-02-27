package crud.app.notesmanager.repositories;

import crud.app.notesmanager.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
