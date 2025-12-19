package org.msa.skillforge_backend.assessment.repository;

import org.msa.skillforge_backend.assessment.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, String> {

    List<Question> findByAssessment_AssessmentId(String assessmentId);
}
