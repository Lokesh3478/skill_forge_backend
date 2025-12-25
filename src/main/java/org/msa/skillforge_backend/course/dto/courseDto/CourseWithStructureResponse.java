package org.msa.skillforge_backend.course.dto.courseDto;

import org.msa.skillforge_backend.course.dto.phase.PhaseResponse;

import java.util.List;

public record CourseWithStructureResponse(
        String courseId,
        String courseName,
        List<PhaseResponse> phases,
        String finalAssessmentId
) {}
