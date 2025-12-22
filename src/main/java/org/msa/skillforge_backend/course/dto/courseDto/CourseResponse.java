package org.msa.skillforge_backend.course.dto.courseDto;

import org.msa.skillforge_backend.course.dto.PhaseSummary;

import java.util.List;

public record CourseResponse(
        String courseId,
        String courseName,
        List<PhaseSummary> phases,
        String finalAssessmentId
) {}
