package org.msa.skillforge_backend.course.dto.courseDto;

import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public record AssignTopicsRequest(
        @NotEmpty(message = "At least one topic must be assigned")
        Set<String> topicIds
) {}

