package org.msa.skillforge_backend.course.dto.phase;

import org.msa.skillforge_backend.course.dto.content.ContentResponse;

import java.util.List;

public record PhaseResponse(
        String phaseId,
        String title,
        String courseId,
        String testId,
        List<ContentResponse> contents
) {}
