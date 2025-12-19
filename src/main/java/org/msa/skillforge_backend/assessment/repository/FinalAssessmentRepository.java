package org.msa.skillforge_backend.assessment.repository;

import org.msa.skillforge_backend.assessment.entity.FinalAssessment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FinalAssessmentRepository extends JpaRepository<FinalAssessment, String> {

    Optional<FinalAssessment> findByCourse_CourseId(String courseId);
}
