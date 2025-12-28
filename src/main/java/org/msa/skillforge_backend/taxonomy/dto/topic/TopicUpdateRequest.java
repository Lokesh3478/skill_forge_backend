package org.msa.skillforge_backend.taxonomy.dto.topic;

import jakarta.validation.constraints.NotBlank;

public record TopicUpdateRequest(
        @NotBlank String name
) {}

