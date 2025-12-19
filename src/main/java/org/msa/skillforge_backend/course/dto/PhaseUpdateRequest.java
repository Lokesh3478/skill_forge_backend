package org.msa.skillforge_backend.course.dto;

import jakarta.validation.constraints.NotBlank;

public record PhaseUpdateRequest(
        @NotBlank(message = "Phase title cannot be empty")
        String title
) {}
