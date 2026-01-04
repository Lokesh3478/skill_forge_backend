package org.msa.skillforge_backend.course_enrollment.dto.courseenrollments;

import lombok.*;
import org.msa.skillforge_backend.course_enrollment.dto.phaseprogress.PhaseProgressDTO;
import org.msa.skillforge_backend.course_enrollment.entity.EnrollmentStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseEnrollmentDetailDTO {

    private String enrollmentId;

    private String courseId;
    private String courseName;

    private EnrollmentStatus status;
    private LocalDateTime enrolledAt;

    private double courseCompletionPercentage;

    private List<PhaseProgressDTO> phases;
}

