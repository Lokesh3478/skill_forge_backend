package org.msa.skillforge_backend.assessment.dto;

import jakarta.validation.constraints.NotBlank;

public record TestCreateRequest(

        @NotBlank
        String phaseId,

        @NotBlank
        String testName
) {}

