package org.msa.skillforge_backend.assessment.dto.question;

import java.util.List;

public record MCQUpdateRequest(
        String questionId,
        String questionText,
        List<String> options,
        String correctAnswer
) {}
