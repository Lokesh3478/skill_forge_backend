package org.msa.skillforge_backend.course_enrollment.repository;

import org.msa.skillforge_backend.course_enrollment.entity.PhaseTestAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhaseTestAttemptRepository
        extends JpaRepository<PhaseTestAttempt, String> {

    @Query("""
        SELECT pta
        FROM PhaseTestAttempt pta
        WHERE pta.phaseProgress.phaseProgressId = :phaseProgressId
        ORDER BY pta.attemptedAt DESC
    """)
    List<PhaseTestAttempt> findAttemptsForPhase(
            @Param("phaseProgressId") String phaseProgressId
    );

    @Query("""
        SELECT COUNT(pta)
        FROM PhaseTestAttempt pta
        WHERE pta.phaseProgress.enrollment.enrollmentId = :enrollmentId
          AND pta.passed = true
    """)
    long countPassedPhaseTests(
            @Param("enrollmentId") String enrollmentId
    );

    @Modifying
    @Query("""
        DELETE FROM PhaseTestAttempt pta
        WHERE pta.phaseProgress.enrollment.enrollmentId = :enrollmentId
    """)
    void deleteByEnrollment(
            @Param("enrollmentId") String enrollmentId
    );
}

