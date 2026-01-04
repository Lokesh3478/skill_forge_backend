package org.msa.skillforge_backend.course_enrollment.repository;
import org.msa.skillforge_backend.course_enrollment.entity.PhaseProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
public interface PhaseProgressRepository
        extends JpaRepository<PhaseProgress, String> {

    Optional<PhaseProgress> findByEnrollment_EnrollmentIdAndPhase_PhaseId(
            String enrollmentId,
            String phaseId
    );

    @Query("""
        SELECT pp
        FROM PhaseProgress pp
        WHERE pp.enrollment.enrollmentId = :enrollmentId
    """)
    List<PhaseProgress> findAllByEnrollment(
            @Param("enrollmentId") String enrollmentId
    );

    @Query("""
        SELECT COUNT(pp)
        FROM PhaseProgress pp
        WHERE pp.enrollment.enrollmentId = :enrollmentId
          AND pp.phaseTestPassed = false
    """)
    long countUnpassedPhases(
            @Param("enrollmentId") String enrollmentId
    );

    List<PhaseProgress> findByEnrollment_EnrollmentId(String enrollmentId);
}

