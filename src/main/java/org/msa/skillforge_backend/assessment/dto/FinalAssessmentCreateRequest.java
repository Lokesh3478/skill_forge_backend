package org.msa.skillforge_backend.assessment.dto;

import jakarta.validation.constraints.NotBlank;

public record FinalAssessmentCreateRequest(

        @NotBlank
        String courseId,
        @NotBlank
        String finalAssessmentName
) {}

