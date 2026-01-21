package crud.app.notesmanager.services.impl;

import crud.app.notesmanager.dtos.CreateNoteRequest;
import crud.app.notesmanager.dtos.NoteResponse;
import crud.app.notesmanager.dtos.UpdateNoteRequest;
import crud.app.notesmanager.mappers.NoteMapper;
import crud.app.notesmanager.repositories.NoteRepository;
import crud.app.notesmanager.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    public Iterable<NoteResponse> getAllNotes() {
        return noteRepository.findAll()
                .stream()
                .map(noteMapper::entityToDto) // .map(note -> noteMapper.entityToDto(note))
                .toList();
    }

    public NoteResponse getNoteById(Long id) {
        return noteMapper.entityToDto(noteRepository.findById(id).orElse(null));
    }

    public NoteResponse createNote(CreateNoteRequest createNoteRequest) {
        var note = noteMapper.dtoToEntity(createNoteRequest);
        var savedNote = noteRepository.save(note); // savedNote has id and timestamps while note doesn't
        return noteMapper.entityToDto(savedNote);
    }

    public NoteResponse updateNote(UpdateNoteRequest updateNoteRequest, Long id) {
        var note = noteRepository.findById(id).orElse(null);
        if (note == null) {
            return null;
        }
        noteMapper.updateNote(updateNoteRequest, note);
        var updatedNote = noteRepository.save(note);
        return noteMapper.entityToDto(updatedNote);
    }
}
