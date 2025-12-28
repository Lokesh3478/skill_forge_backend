package org.msa.skillforge_backend.course.dto.courseDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record CourseCreateRequest(
        @NotBlank
        @Size(min = 10, max = 100)
        String courseName,
        Set<String>topicIds
) {}
