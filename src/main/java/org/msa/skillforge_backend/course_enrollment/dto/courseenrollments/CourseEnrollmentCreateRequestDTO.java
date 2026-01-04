package org.msa.skillforge_backend.course_enrollment.dto.courseenrollments;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseEnrollmentCreateRequestDTO {

    private String courseId;
}

