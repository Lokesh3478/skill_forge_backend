package org.msa.skillforge_backend.assessment.service;

import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.assessment.dto.MCQCreateRequest;
import org.msa.skillforge_backend.assessment.dto.MCQResponse;
import org.msa.skillforge_backend.assessment.entity.Assessment;
import org.msa.skillforge_backend.assessment.entity.MCQQuestion;
import org.msa.skillforge_backend.assessment.repository.AssessmentRepository;
import org.msa.skillforge_backend.assessment.repository.MCQQuestionRepository;
import org.msa.skillforge_backend.assessment.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final AssessmentRepository assessmentRepository;
    private final QuestionRepository questionRepository;
    private final MCQQuestionRepository mcqQuestionRepository;

    /* ---------------- CREATE MCQ ---------------- */

    public MCQResponse createMCQ(MCQCreateRequest request) {

        Assessment assessment = assessmentRepository.findById(request.assessmentId())
                .orElseThrow(() -> new NoSuchElementException("Assessment not found"));

        MCQQuestion question = MCQQuestion.builder()
                .questionText(request.questionText())
                .options(request.options())
                .correctAnswer(request.correctAnswer())
                .assessment(assessment)
                .build();

        MCQQuestion saved = mcqQuestionRepository.save(question);

        return mapToMCQResponse(saved);
    }

    /* ---------------- READ ---------------- */

    public List<MCQResponse> getQuestionsByAssessment(String assessmentId) {

        return questionRepository.findByAssessment_AssessmentId(assessmentId)
                .stream()
                .filter(q -> q instanceof MCQQuestion)
                .map(q -> mapToMCQResponse((MCQQuestion) q))
                .toList();
    }

    /* ---------------- DELETE ---------------- */

    public void deleteQuestion(String questionId) {

        if (!questionRepository.existsById(questionId)) {
            throw new NoSuchElementException("Question not found");
        }

        questionRepository.deleteById(questionId);
    }

    /* ---------------- MAPPER ---------------- */

    private MCQResponse mapToMCQResponse(MCQQuestion question) {
        return new MCQResponse(
                question.getQuestionId(),
                question.getQuestionText(),
                question.getOptions()
        );
    }
}
