package org.msa.skillforge_backend.course_enrollment.dto.finalassessmentattempt;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinalAssessmentAttemptDTO {

    private String attemptId;

    private int score;
    private int maxScore;

    private boolean completed;
    private boolean passed;

    private LocalDateTime attemptedAt;
}

