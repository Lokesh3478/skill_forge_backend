package org.msa.skillforge_backend.taxonomy.dto.fieldOfStudy;

import org.msa.skillforge_backend.taxonomy.entity.TaxonomyStatus;

public record FieldResponse(
        String fieldId,
        String name,
        String description,
        TaxonomyStatus status
) {}

