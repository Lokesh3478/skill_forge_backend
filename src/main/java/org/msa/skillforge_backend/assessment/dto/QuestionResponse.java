package org.msa.skillforge_backend.assessment.dto;

public record QuestionResponse(
        String questionId,
        String questionText,
        String type
) {}
