package org.msa.skillforge_backend.course_enrollment.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "phase_test_attempt")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhaseTestAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String attemptId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "phase_progress_id", nullable = false)
    private PhaseProgress phaseProgress;

    private int score;
    private int maxScore;

    private boolean completed;
    private boolean passed;

    private LocalDateTime attemptedAt;
}

