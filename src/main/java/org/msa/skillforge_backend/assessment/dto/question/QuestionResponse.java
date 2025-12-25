package org.msa.skillforge_backend.assessment.dto.question;

public record QuestionResponse(
        String questionId,
        String questionText,
        String type
) {}
