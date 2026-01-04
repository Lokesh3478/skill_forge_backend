package org.msa.skillforge_backend.course_enrollment.repository;

import org.msa.skillforge_backend.course_enrollment.entity.FinalAssessmentAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FinalAssessmentAttemptRepository
        extends JpaRepository<FinalAssessmentAttempt, String> {

    @Query("""
        SELECT faa
        FROM FinalAssessmentAttempt faa
        WHERE faa.enrollment.enrollmentId = :enrollmentId
        ORDER BY faa.attemptedAt DESC
    """)
    List<FinalAssessmentAttempt> findAttemptsForEnrollment(
            @Param("enrollmentId") String enrollmentId
    );

    @Query("""
        SELECT COUNT(faa)
        FROM FinalAssessmentAttempt faa
        WHERE faa.enrollment.enrollmentId = :enrollmentId
          AND faa.passed = true
    """)
    long countPassedFinalAttempts(
            @Param("enrollmentId") String enrollmentId
    );

    @Modifying
    @Query("""
        DELETE FROM FinalAssessmentAttempt faa
        WHERE faa.enrollment.enrollmentId = :enrollmentId
    """)
    void deleteByEnrollment(
            @Param("enrollmentId") String enrollmentId
    );

    List<FinalAssessmentAttempt> findByEnrollment_EnrollmentId(String enrollmentId);

    void deleteByEnrollment_EnrollmentId(String enrollmentId);
}

