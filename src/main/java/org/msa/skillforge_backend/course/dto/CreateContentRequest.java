package org.msa.skillforge_backend.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateContentRequest {

        @NotBlank(message = "Content name cannot be empty")
        private String contentName;

        @NotBlank(message = "Content URL cannot be empty")
        private String contentUrl;

        @NotNull(message = "Phase ID is required")
        private String phaseId;
}
