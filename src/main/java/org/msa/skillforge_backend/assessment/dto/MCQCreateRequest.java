package org.msa.skillforge_backend.assessment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record MCQCreateRequest(

        @NotBlank
        String assessmentId,

        @NotBlank
        String questionText,

        @NotEmpty
        @Size(min = 2)
        List<String> options,

        @NotBlank
        String correctAnswer

) implements QuestionCreateRequest {

    @Override
    public String type() {
        return "MCQ";
    }
}
