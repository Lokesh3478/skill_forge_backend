package org.msa.skillforge_backend.assessment.dto.assessment;

public record AssessmentResponse(
        String assessmentId,
        AssessmentType type,
        String phaseId,
        String courseId,
        Integer durationInMinutes) {}


