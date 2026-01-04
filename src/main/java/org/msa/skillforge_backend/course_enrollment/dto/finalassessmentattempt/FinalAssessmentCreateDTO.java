package org.msa.skillforge_backend.course_enrollment.dto.finalassessmentattempt;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinalAssessmentCreateDTO {

    private String enrollmentId;
    private String finalAssessmentId;
}

