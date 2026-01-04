package org.msa.skillforge_backend.assessment.dto.assessment;


import org.msa.skillforge_backend.assessment.dto.question.MCQResponse;

import java.util.List;

public record AssessmentWithQuestionsResponse(
        String assessmentId,
        AssessmentType type,
        String phaseId,
        String courseId,
        List<MCQResponse> questions,
        Integer durationInMinutes,
        int page,
        int size,
        long totalElements,
        int totalPages
) {}
