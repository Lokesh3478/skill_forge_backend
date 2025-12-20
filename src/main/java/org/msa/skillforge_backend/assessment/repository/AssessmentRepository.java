package org.msa.skillforge_backend.assessment.repository;

import org.msa.skillforge_backend.assessment.entity.Assessment;
import org.msa.skillforge_backend.assessment.entity.FinalAssessment;
import org.msa.skillforge_backend.assessment.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AssessmentRepository extends JpaRepository<Assessment, String> {

    @Query("""
        SELECT t FROM Test t
        JOIN FETCH t.phase
        WHERE t.assessmentId = :id
    """)
    Optional<Test> findTestWithPhase(String id);

    @Query("""
        SELECT f FROM FinalAssessment f
        JOIN FETCH f.course
        WHERE f.assessmentId = :id
    """)
    Optional<FinalAssessment> findFinalWithCourse(String id);
}
