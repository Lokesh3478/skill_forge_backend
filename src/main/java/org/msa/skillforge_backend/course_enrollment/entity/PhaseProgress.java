package org.msa.skillforge_backend.course_enrollment.entity;

import jakarta.persistence.*;
import lombok.*;
import org.msa.skillforge_backend.course.entity.Phase;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "phase_progress",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"enrollment_id", "phase_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhaseProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String phaseProgressId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private CourseEnrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "phase_id", nullable = false)
    private Phase phase;

    private boolean phaseTestPassed;

    @OneToMany(mappedBy = "phaseProgress", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PhaseTestAttempt> phaseTestAttempts = new HashSet<>();
}

