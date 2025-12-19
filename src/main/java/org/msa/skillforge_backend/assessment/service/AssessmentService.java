package org.msa.skillforge_backend.assessment.service;

import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.assessment.dto.AssessmentResponse;
import org.msa.skillforge_backend.assessment.dto.AssessmentType;
import org.msa.skillforge_backend.assessment.entity.Assessment;
import org.msa.skillforge_backend.assessment.entity.FinalAssessment;
import org.msa.skillforge_backend.assessment.entity.Test;
import org.msa.skillforge_backend.assessment.repository.FinalAssessmentRepository;
import org.msa.skillforge_backend.assessment.repository.TestRepository;
import org.msa.skillforge_backend.course.entity.Course;
import org.msa.skillforge_backend.course.entity.Phase;
import org.msa.skillforge_backend.course.repository.CourseRepository;
import org.msa.skillforge_backend.course.repository.PhaseRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AssessmentService {

    private final TestRepository testRepository;
    private final FinalAssessmentRepository finalAssessmentRepository;
    private final PhaseRepository phaseRepository;
    private final CourseRepository courseRepository;

    /* ---------------- CREATE TEST ---------------- */

    public AssessmentResponse createTestForPhase(String phaseId, String testName) {

        if (testRepository.existsByPhase_PhaseId(phaseId)) {
            throw new IllegalStateException("Test already exists for this phase");
        }

        Phase phase = phaseRepository.findById(phaseId)
                .orElseThrow(() -> new NoSuchElementException("Phase not found"));

        Test test = Test.builder()
                .testName(testName)
                .phase(phase)
                .build();

        phase.setTest(test);

        Test saved = testRepository.save(test);

        return mapToResponse(saved);
    }

    /* ---------------- CREATE FINAL ASSESSMENT ---------------- */

    public AssessmentResponse createFinalAssessmentForCourse(String courseId) {

        if (finalAssessmentRepository.existsByCourse_CourseId(courseId)) {
            throw new IllegalStateException("Final assessment already exists for this course");
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));

        FinalAssessment finalAssessment = FinalAssessment.builder()
                .course(course)
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
                    test.getPhase().getPhaseId(),
                    null
            );
        }

        if (assessment instanceof FinalAssessment finalAssessment) {
            return new AssessmentResponse(
                    finalAssessment.getAssessmentId(),
                    AssessmentType.FINAL,
                    null,
                    finalAssessment.getCourse().getCourseId()
            );
        }

        throw new IllegalStateException("Unknown assessment type");
    }

}
