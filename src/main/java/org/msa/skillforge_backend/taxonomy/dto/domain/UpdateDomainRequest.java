package org.msa.skillforge_backend.taxonomy.dto.domain;

import jakarta.validation.constraints.NotBlank;

public record UpdateDomainRequest(
        @NotBlank String name
) {}

