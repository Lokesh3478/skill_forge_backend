package org.msa.skillforge_backend.course_enrollment.dto.courseenrollments;

import lombok.*;
import org.msa.skillforge_backend.course_enrollment.entity.EnrollmentStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseEnrollmentResponseDTO {

    private String enrollmentId;
    private String courseId;
    private String courseName;

    private EnrollmentStatus status;
    private LocalDateTime enrolledAt;
}

