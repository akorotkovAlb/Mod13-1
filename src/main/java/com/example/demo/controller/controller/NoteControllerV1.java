package com.example.demo.controller.controller;

import com.example.demo.service.dto.NoteDto;
import com.example.demo.service.exception.NoteNotFoundException;
import com.example.demo.service.mapper.NoteMapper;
import com.example.demo.service.service.NoteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Validated
@Controller
@RequestMapping("/V1/notes")
public class NoteControllerV1 {

    @Autowired private NoteService noteService;
    @Autowired private NoteMapper noteMapper;

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public ModelAndView noteList() {
        ModelAndView result = new ModelAndView("notes/allNotes");
        result.addObject("notes", noteMapper.toNoteResponses(noteService.listAll()));
        return result;
    }

    @RequestMapping(value = "/edit", method = {RequestMethod.GET})
    public ModelAndView getNoteForEdit(@NotNull @RequestParam(value="id") UUID id) throws NoteNotFoundException {
        ModelAndView result = new ModelAndView("notes/updatesNotes");
        result.addObject("note", noteMapper.toNoteResponse(noteService.getById(id)));
        return result;
    }

    @RequestMapping(value = "/create", method = {RequestMethod.POST})
    public ModelAndView createNote(
            @RequestParam(value="title") @Size(min = 1, max = 250) String title,
            @RequestParam(value="content") @NotBlank String content) {
        NoteDto dto = new NoteDto();
        dto.setTitle(title);
        dto.setContent(content);
        noteService.add(dto);
        return noteList();
    }

    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public ModelAndView updateNote(
            @NotNull @RequestParam(value="id") UUID id,
            @Size(min = 1, max = 250) @RequestParam(value="title") String title,
            @NotEmpty @RequestParam(value="content") String content) throws NoteNotFoundException {
        NoteDto dto = new NoteDto();
        dto.setId(id);
        dto.setTitle(title);
        dto.setContent(content);
        noteService.update(dto);
        return noteList();
    }

    @DeleteMapping("/delete")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public ModelAndView deleteNoteById(@Valid @NotNull @RequestParam(value="id") UUID id) throws NoteNotFoundException {
        noteService.deleteById(id);
        return noteList();
    }
}
