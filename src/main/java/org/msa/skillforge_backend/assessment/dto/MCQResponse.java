package org.msa.skillforge_backend.assessment.dto;

import java.util.List;

public record MCQResponse(
        String questionId,
        String questionText,
        List<String> options
) {}
