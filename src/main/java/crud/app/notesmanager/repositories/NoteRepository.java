package crud.app.notesmanager.repositories;

import crud.app.notesmanager.entities.Note;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    @EntityGraph(attributePaths = "author")
    @Query("SELECT n FROM Note n")
    List<Note> findAllWithAuthors();

    @EntityGraph(attributePaths = "author")
    List<Note> findAllByAuthorId(Integer id);
}
