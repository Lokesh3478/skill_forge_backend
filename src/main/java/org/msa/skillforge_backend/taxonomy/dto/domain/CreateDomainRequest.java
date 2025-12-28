package org.msa.skillforge_backend.taxonomy.dto.domain;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateDomainRequest(
        @NotBlank String name,
        @NotNull String fieldId
) {}

