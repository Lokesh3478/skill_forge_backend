package org.msa.skillforge_backend.course.dto.courseDto;

public record CourseSummary(
        String courseId,
        String courseName,
        Integer durationInSeconds
) {}
