package org.msa.skillforge_backend.course_enrollment.repository;

import org.msa.skillforge_backend.course_enrollment.entity.ContentProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ContentProgressRepository
        extends JpaRepository<ContentProgress, String> {

    Optional<ContentProgress> findByEnrollment_EnrollmentIdAndContent_ContentId(
            String enrollmentId,
            String contentId
    );

    @Query("""
        SELECT COALESCE(SUM(cp.secondsSpent), 0)
        FROM ContentProgress cp
        WHERE cp.enrollment.enrollmentId = :enrollmentId
          AND cp.content.phase.phaseId = :phaseId
    """)
    long getTotalSecondsSpentForPhase(
            @Param("enrollmentId") String enrollmentId,
            @Param("phaseId") String phaseId
    );

    @Modifying
    @Query("""
        DELETE FROM ContentProgress cp
        WHERE cp.enrollment.enrollmentId = :enrollmentId
    """)
    void deleteByEnrollment(
            @Param("enrollmentId") String enrollmentId
    );
}

