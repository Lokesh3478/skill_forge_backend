package org.msa.skillforge_backend.course.dto;

import java.util.List;

public record PhaseResponse(
        String phaseId,
        String title,
        String courseId,
        String testId,
        List<ContentResponse> contents
) {}
