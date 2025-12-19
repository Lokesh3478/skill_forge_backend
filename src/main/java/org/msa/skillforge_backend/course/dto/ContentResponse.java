package org.msa.skillforge_backend.course.dto;
public record ContentResponse(
        String contentId,
        String contentName,
        String contentUrl,
        String phaseId
) {}
