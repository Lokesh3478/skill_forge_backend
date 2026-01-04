package org.msa.skillforge_backend.course_enrollment.entity;
import jakarta.persistence.*;
import lombok.*;
import org.msa.skillforge_backend.assessment.entity.FinalAssessment;

import java.time.LocalDateTime;

@Entity
@Table(name = "final_assessment_attempt")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinalAssessmentAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String attemptId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private CourseEnrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "final_assessment_id", nullable = false)
    private FinalAssessment finalAssessment;

    private int score;
    private int maxScore;

    private boolean completed;
    private boolean passed;

    private LocalDateTime attemptedAt;
}

