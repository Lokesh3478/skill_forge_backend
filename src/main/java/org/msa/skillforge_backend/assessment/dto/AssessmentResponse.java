package org.msa.skillforge_backend.assessment.dto;

public record AssessmentResponse(
        String assessmentId,
        AssessmentType type,
        String phaseId,
        String courseId
) {}


