package org.msa.skillforge_backend.assessment.service;

import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.assessment.dto.assessment.*;
import org.msa.skillforge_backend.assessment.dto.question.MCQResponse;
import org.msa.skillforge_backend.assessment.entity.Assessment;
import org.msa.skillforge_backend.assessment.entity.FinalAssessment;
import org.msa.skillforge_backend.assessment.entity.MCQQuestion;
import org.msa.skillforge_backend.assessment.entity.Test;
import org.msa.skillforge_backend.assessment.repository.FinalAssessmentRepository;
import org.msa.skillforge_backend.assessment.repository.MCQQuestionRepository;
import org.msa.skillforge_backend.assessment.repository.TestRepository;
import org.msa.skillforge_backend.course.entity.Course;
import org.msa.skillforge_backend.course.entity.Phase;
import org.msa.skillforge_backend.course.repository.CourseRepository;
import org.msa.skillforge_backend.course.repository.PhaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AssessmentService {

    private final TestRepository testRepository;
    private final FinalAssessmentRepository finalAssessmentRepository;
    private final PhaseRepository phaseRepository;
    private final CourseRepository courseRepository;
    private final MCQQuestionRepository mcqQuestionRepository;

    /* ---------------- CREATE TEST ---------------- */

    public AssessmentResponse createTestForPhase(TestCreateRequest testCreateRequest) {

        String phaseId = testCreateRequest.phaseId();
        Integer durationInMinutes = testCreateRequest.durationInMinutes();
        String testName = testCreateRequest.testName();

        if (testRepository.existsByPhase_PhaseId(phaseId)) {
            throw new IllegalStateException("Test already exists for this phase");
        }

        Phase phase = phaseRepository.findById(phaseId)
                .orElseThrow(() -> new NoSuchElementException("Phase not found"));

        Test test = Test.builder()
                .testName(testName)
                .phase(phase)
                .durationInMinutes(durationInMinutes)
                .build();

        phase.setTest(test);

        Test saved = testRepository.save(test);

        return mapToResponse(saved);
    }

    /* ---------------- CREATE FINAL ASSESSMENT ---------------- */

    public AssessmentResponse createFinalAssessmentForCourse(FinalAssessmentCreateRequest createRequest) {

        String courseId = createRequest.courseId();
        String name = createRequest.finalAssessmentName();
        Integer durationInMinutes = createRequest.durationInMinutes();
        if (finalAssessmentRepository.existsByCourse_CourseId(courseId)) {
            throw new IllegalStateException("Final assessment already exists for this course");
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));

        FinalAssessment finalAssessment = FinalAssessment.builder()
                .course(course)
                .durationInMinutes(durationInMinutes)
                .build();

        course.setFinalAssessment(finalAssessment);

        FinalAssessment saved = finalAssessmentRepository.save(finalAssessment);

        return mapToResponse(saved);
    }

    /* ---------------- READ ---------------- */

    public AssessmentResponse getAssessmentById(String assessmentId) {

        Assessment assessment = testRepository.findById(assessmentId)
                .map(a -> (Assessment) a)
                .orElseGet(() ->
                        finalAssessmentRepository.findById(assessmentId)
                                .orElseThrow(() ->
                                        new NoSuchElementException("Assessment not found"))
                );

        return mapToResponse(assessment);
    }


    /* ---------------- DELETE ---------------- */

    public void deleteAssessment(String assessmentId) {

        if (testRepository.existsById(assessmentId)) {
            testRepository.deleteById(assessmentId);
            return;
        }

        if (finalAssessmentRepository.existsById(assessmentId)) {
            finalAssessmentRepository.deleteById(assessmentId);
            return;
        }

        throw new NoSuchElementException("Assessment not found");
    }

    /* ---------------- MAPPER ---------------- */

    private AssessmentResponse mapToResponse(Assessment assessment) {

        if (assessment instanceof Test test) {
            return new AssessmentResponse(
                    test.getAssessmentId(),
                    AssessmentType.TEST,
                    test.getPhase() != null ? test.getPhase().getPhaseId() : null,
                    null,
                    test.getDurationInMinutes()
            );
        }

        if (assessment instanceof FinalAssessment finalAssessment) {
            return new AssessmentResponse(
                    finalAssessment.getAssessmentId(),
                    AssessmentType.FINAL,
                    null,
                    finalAssessment.getCourse() != null
                            ? finalAssessment.getCourse().getCourseId()
                            : null,
                    finalAssessment.getDurationInMinutes()
            );
        }

        throw new IllegalStateException(
                "Unsupported assessment subtype: " + assessment.getClass().getName()
        );
    }
    public AssessmentWithQuestionsResponse getAssessmentWithQuestions(
            String assessmentId,
            Pageable pageable
    ) {

        Assessment assessment = testRepository.findById(assessmentId)
                .map(a -> (Assessment) a)
                .orElseGet(() ->
                        finalAssessmentRepository.findById(assessmentId)
                                .orElseThrow(() ->
                                        new NoSuchElementException("Assessment not found"))
                );

        Page<MCQQuestion> questionPage =
                mcqQuestionRepository.findByAssessment_AssessmentId(
                        assessmentId,
                        pageable
                );

        return new AssessmentWithQuestionsResponse(
                assessment.getAssessmentId(),
                assessment instanceof org.msa.skillforge_backend.assessment.entity.Test
                        ? AssessmentType.TEST
                        : AssessmentType.FINAL,
                assessment instanceof org.msa.skillforge_backend.assessment.entity.Test test
                        ? test.getPhase().getPhaseId()
                        : null,
                assessment instanceof org.msa.skillforge_backend.assessment.entity.FinalAssessment fa
                        ? fa.getCourse().getCourseId()
                        : null,
                questionPage.getContent().stream()
                        .map(q -> new MCQResponse(
                                q.getQuestionId(),
                                q.getQuestionText(),
                                q.getOptions()
                        ))
                        .toList(),
                assessment.getDurationInMinutes(),
                questionPage.getNumber(),
                questionPage.getSize(),
                questionPage.getTotalElements(),
                questionPage.getTotalPages()
        );
    }

}
