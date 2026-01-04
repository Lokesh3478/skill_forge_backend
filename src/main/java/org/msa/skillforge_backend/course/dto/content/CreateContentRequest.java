package org.msa.skillforge_backend.course.dto.content;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
public record CreateContentRequest (

        @NotBlank(message = "Content name cannot be empty")
        String contentName,

        @NotBlank(message = "Content URL cannot be empty")
        String contentUrl,

        @NotNull(message = "Phase ID is required")
        String phaseId,

        @NotNull
                @Min(1)
                @Max(300)
                @Column(nullable = false)
        Integer durationInSeconds
){}
