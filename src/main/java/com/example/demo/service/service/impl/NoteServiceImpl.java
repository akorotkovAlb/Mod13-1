package com.example.demo.service.service.impl;

import com.example.demo.data.entity.NoteEntity;
import com.example.demo.data.repository.NoteRepository;
import com.example.demo.service.dto.NoteDto;
import com.example.demo.service.exception.NoteNotFoundException;
import com.example.demo.service.mapper.NoteMapper;
import com.example.demo.service.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class NoteServiceImpl implements NoteService {

    @Autowired private NoteRepository noteRepository;
    @Autowired private NoteMapper noteMapper;

    @Override
    public List<NoteDto> listAll() {
        return noteMapper.toNoteDtos(noteRepository.findAll());
    }

    @Override
    public NoteDto add(NoteDto note) {
        NoteEntity entity = noteMapper.toNoteEntity(note);
        entity.setId(null);
        return noteMapper.toNoteDto(noteRepository.save(entity));
    }

    @Override
    public List<NoteDto> addAll(Collection<NoteDto> notes) {
        Collection<NoteEntity> savedNotes = noteRepository.saveAll(noteMapper.toNoteEntities(notes));
        return noteMapper.toNoteDtos(savedNotes);
    }

    @Override
    public void deleteById(UUID id) throws NoteNotFoundException {
        getById(id);
        noteRepository.deleteById(id);
    }

    @Override
    public void update(NoteDto note) throws NoteNotFoundException {
        if (Objects.isNull(note.getId())) {
            throw new NoteNotFoundException();
        }
        getById(note.getId());
        noteRepository.save(noteMapper.toNoteEntity(note));
    }

    @Override
    public NoteDto getById(UUID id) throws NoteNotFoundException {
        Optional<NoteEntity> optionalNote = noteRepository.findById(id);
        if (optionalNote.isPresent()) {
            return noteMapper.toNoteDto(optionalNote.get());
        } else {
            throw new NoteNotFoundException(id);
        }
    }
}
