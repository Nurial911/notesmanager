package crud.app.notesmanager.services.impl;

import crud.app.notesmanager.dtos.CreateNoteRequest;
import crud.app.notesmanager.dtos.NoteResponse;
import crud.app.notesmanager.dtos.UpdateNoteRequest;
import crud.app.notesmanager.entities.Note;
import crud.app.notesmanager.mappers.NoteMapper;
import crud.app.notesmanager.repositories.AuthorRepository;
import crud.app.notesmanager.repositories.NoteRepository;
import crud.app.notesmanager.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;
    private final AuthorRepository authorRepository;

    public Iterable<NoteResponse> getAllNotes() {
        List<Note> notes = noteRepository.findAllWithAuthors();
        return notes
                .stream()
                .map(noteMapper::entityToDto) // .map(note -> noteMapper.entityToDto(note))
                .toList();
    }

    public Iterable<NoteResponse> getAllNotesByAuthorId(Integer authorId) {
        List<Note> notes = noteRepository.findAllByAuthorId(authorId);
        return notes
                .stream()
                .map(noteMapper::entityToDto)
                .toList();
    }

    public NoteResponse getNoteById(Long id) {
        return noteMapper.entityToDto(noteRepository.findById(id).orElse(null));
    }

    public NoteResponse createNote(CreateNoteRequest createNoteRequest) {
        var author = authorRepository.findById(createNoteRequest.getAuthorId()).orElse(null);
        if (author == null) {
            return null;
        }
        var note = noteMapper.dtoToEntity(createNoteRequest);
        note.setAuthor(author);
        var savedNote = noteRepository.save(note); // savedNote has id and timestamps while note doesn't
        return noteMapper.entityToDto(savedNote);
    }

    public NoteResponse updateNote(UpdateNoteRequest updateNoteRequest, Long id) {
        var note = noteRepository.findById(id).orElse(null);
        var author = authorRepository.findById(updateNoteRequest.getAuthorId()).orElse(null);
        if (author == null) {
            return null;
        }
        if (note == null) {
            return null;
        }
        noteMapper.updateNote(updateNoteRequest, note);
        note.setAuthor(author);
        var updatedNote = noteRepository.save(note);
        return noteMapper.entityToDto(updatedNote);
    }

    public boolean deleteNote(Long id) {
        var  note = noteRepository.findById(id).orElse(null);
        if (note == null) {
            return false;
        }
        noteRepository.delete(note);
        return true;
    }
}
