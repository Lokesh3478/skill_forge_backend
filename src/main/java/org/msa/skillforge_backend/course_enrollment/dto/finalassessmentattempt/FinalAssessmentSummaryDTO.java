package org.msa.skillforge_backend.course_enrollment.dto.finalassessmentattempt;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinalAssessmentSummaryDTO {

    private String finalAssessmentId;
    private String title;

    private boolean eligibleToAttempt;
    private boolean completed;
    private boolean passed;
}

