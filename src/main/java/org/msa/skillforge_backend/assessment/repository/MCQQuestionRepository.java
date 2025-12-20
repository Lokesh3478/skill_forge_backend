package org.msa.skillforge_backend.assessment.repository;

import org.msa.skillforge_backend.assessment.entity.MCQQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MCQQuestionRepository extends JpaRepository<MCQQuestion, String> {
    Page<MCQQuestion> findByAssessment_AssessmentId(
            String assessmentId,
            Pageable pageable
    );

}

