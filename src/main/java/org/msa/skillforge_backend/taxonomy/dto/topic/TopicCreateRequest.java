package org.msa.skillforge_backend.taxonomy.dto.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicCreateRequest(
        @NotBlank String name,
        @NotNull String domainId
) {}

