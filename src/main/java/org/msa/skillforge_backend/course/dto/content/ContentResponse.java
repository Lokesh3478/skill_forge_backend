package org.msa.skillforge_backend.course.dto.content;
public record ContentResponse(
        String contentId,
        String contentName,
        String contentUrl,
        String phaseId,
        Integer durationInSeconds) {}
