package org.msa.skillforge_backend.assessment.dto.assessment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FinalAssessmentCreateRequest(

        @NotBlank
        String courseId,
        @NotBlank
        String finalAssessmentName,
        @NotNull
        Integer durationInMinutes
) {}

