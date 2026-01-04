package org.msa.skillforge_backend.course_enrollment.dto.phasetestattempt;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhaseTestAttemptDTO {

    private String attemptId;

    private int score;
    private int maxScore;

    private boolean passed;
    private boolean completed;

    private LocalDateTime attemptedAt;
}

