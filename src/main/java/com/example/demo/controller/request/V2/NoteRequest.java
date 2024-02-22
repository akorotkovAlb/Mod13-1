package com.example.demo.controller.request.V2;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public abstract class NoteRequest {

    @Size(min = 3, max = 250)
    private String title;

    @NotBlank
    private String content;
}
