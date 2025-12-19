package org.msa.skillforge_backend.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CourseCreateRequest(
        @NotBlank
        @Size(min = 10, max = 100)
        String courseName
) {}
