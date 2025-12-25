package org.msa.skillforge_backend.course.dto.phase;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PhaseCreateRequest(
        @NotBlank(message = "Phase title cannot be empty")
        String title,

        @NotNull(message = "Course ID is required")
        String courseId
) {}
