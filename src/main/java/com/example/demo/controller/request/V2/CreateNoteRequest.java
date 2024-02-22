package com.example.demo.controller.request.V2;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNoteRequest {

    @Size(min = 3, max = 250)
    private String title;

    @NotBlank
    private String content;

    public CreateNoteRequest () {
    }

    public CreateNoteRequest (String title, String content) {
        this.title = title;
        this.content = content;
    }

    @AssertFalse(message = "Title and content must be 5 chars.")
    public boolean isValidateContent() {
        return (title.length() == 5 && content.length() == 5);
    }
}
