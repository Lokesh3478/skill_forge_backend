package org.msa.skillforge_backend.taxonomy.dto.domain;

public record DomainResponse(
        String domainId,
        String name,
        String fieldId,
        String fieldName
) {}

