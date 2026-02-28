package crud.app.notesmanager.repositories;

import crud.app.notesmanager.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
