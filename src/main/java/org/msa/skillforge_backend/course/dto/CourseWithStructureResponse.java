package org.msa.skillforge_backend.course.dto;

import java.util.List;

public record CourseWithStructureResponse(
        String courseId,
        String courseName,
        List<PhaseResponse> phases,
        String finalAssessmentId
) {}
