package org.msa.skillforge_backend.assessment.repository;

import org.msa.skillforge_backend.assessment.entity.MCQQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MCQQuestionRepository extends JpaRepository<MCQQuestion, String> {
}

