package org.msa.skillforge_backend.course.dto;

import java.util.List;

public record CourseResponse(
        String courseId,
        String courseName,
        List<PhaseSummary> phases,
        String finalAssessmentId
) {}
