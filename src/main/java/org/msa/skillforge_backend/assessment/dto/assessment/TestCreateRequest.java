package org.msa.skillforge_backend.assessment.dto.assessment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TestCreateRequest(

        @NotBlank
        String phaseId,

        @NotBlank
        String testName,

        @NotNull
        Integer durationInMinutes
) {}

