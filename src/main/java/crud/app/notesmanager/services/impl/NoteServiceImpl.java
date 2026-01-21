package crud.app.notesmanager.services.impl;

import crud.app.notesmanager.dtos.NoteResponse;
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
}
