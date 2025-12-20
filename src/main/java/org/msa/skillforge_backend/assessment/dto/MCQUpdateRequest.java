package org.msa.skillforge_backend.assessment.dto;

import java.util.List;

public record MCQUpdateRequest(
        String questionText,
        List<String> options,
        String correctAnswer
) {}
