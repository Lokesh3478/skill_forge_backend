package org.msa.skillforge_backend.course_enrollment.dto.courseenrollments;

import lombok.*;
import org.msa.skillforge_backend.course_enrollment.entity.EnrollmentStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseEnrollmentListDTO {

    private String enrollmentId;
    private String courseId;
    private String courseName;

    private EnrollmentStatus status;
    private double completionPercentage;
}

