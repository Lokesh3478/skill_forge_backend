package org.msa.skillforge_backend.course.dto.content;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateContentRequest {

    @NotBlank(message = "Content name cannot be empty")
    private String contentName;

    @NotBlank(message = "Content URL cannot be empty")
    private String contentUrl;
}
