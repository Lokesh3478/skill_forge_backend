package org.msa.skillforge_backend.course_enrollment.dto.finalassessmentattempt;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinalAssessmentDetailDTO {

    private String finalAssessmentId;
    private String title;
    private String description;

    private boolean eligibleToAttempt;

    private List<FinalAssessmentAttemptDTO> attempts;
}

