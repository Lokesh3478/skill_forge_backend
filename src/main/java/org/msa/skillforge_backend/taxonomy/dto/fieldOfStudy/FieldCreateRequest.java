package org.msa.skillforge_backend.taxonomy.dto.fieldOfStudy;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FieldCreateRequest(
        @NotBlank
        @Size(min = 3, max = 100)
        String name,

        @Size(max = 500)
        String description
) {}
