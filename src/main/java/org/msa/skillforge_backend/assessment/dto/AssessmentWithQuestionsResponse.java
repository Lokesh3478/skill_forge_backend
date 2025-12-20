package org.msa.skillforge_backend.assessment.dto;


import java.util.List;

public record AssessmentWithQuestionsResponse(
        String assessmentId,
        AssessmentType type,
        String phaseId,
        String courseId,
        List<MCQResponse> questions,
        int page,
        int size,
        long totalElements,
        int totalPages
) {}
