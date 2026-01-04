package org.msa.skillforge_backend.course_enrollment.service;
import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.assessment.entity.FinalAssessment;
import org.msa.skillforge_backend.assessment.repository.FinalAssessmentRepository;
import org.msa.skillforge_backend.course.entity.Content;
import org.msa.skillforge_backend.course.entity.Course;
import org.msa.skillforge_backend.course.repository.ContentRepository;
import org.msa.skillforge_backend.course.repository.CourseRepository;
import org.msa.skillforge_backend.course.repository.PhaseRepository;
import org.msa.skillforge_backend.course_enrollment.dto.courseenrollments.CourseEnrollmentDetailDTO;
import org.msa.skillforge_backend.course_enrollment.dto.courseenrollments.CourseEnrollmentListDTO;
import org.msa.skillforge_backend.course_enrollment.dto.courseenrollments.CourseEnrollmentResponseDTO;
import org.msa.skillforge_backend.course_enrollment.dto.phaseprogress.PhaseProgressDTO;
import org.msa.skillforge_backend.course_enrollment.dto.phaseprogress.PhaseProgressDetailDTO;
import org.msa.skillforge_backend.course_enrollment.entity.*;
import org.msa.skillforge_backend.course_enrollment.repository.*;
import org.msa.skillforge_backend.user.entity.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FinalAssessmentAttemptService {

    private final CourseEnrollmentRepository enrollmentRepository;
    private final PhaseProgressRepository phaseProgressRepository;
    private final FinalAssessmentRepository finalAssessmentRepository;
    private final FinalAssessmentAttemptRepository finalAssessmentAttemptRepository;

    // ------------------------------------------------
    // ELIGIBILITY CHECK
    // ------------------------------------------------
    public boolean isEligibleForFinalAssessment(String enrollmentId) {

        List<PhaseProgress> phaseProgresses =
                phaseProgressRepository.findByEnrollment_EnrollmentId(enrollmentId);

        if (phaseProgresses.isEmpty()) return false;

        return phaseProgresses.stream()
                .allMatch(PhaseProgress::isPhaseTestPassed);
    }

    // ------------------------------------------------
    // START / SUBMIT FINAL ASSESSMENT ATTEMPT
    // ------------------------------------------------
    public FinalAssessmentAttempt submitFinalAssessment(
            String enrollmentId,
            String finalAssessmentId,
            int score,
            int passMark
    ) {
        CourseEnrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        if (!isEligibleForFinalAssessment(enrollmentId)) {
            throw new RuntimeException("Final assessment not eligible");
        }

        FinalAssessment finalAssessment = finalAssessmentRepository.findById(finalAssessmentId)
                .orElseThrow(() -> new RuntimeException("Final assessment not found"));

        FinalAssessmentAttempt attempt = FinalAssessmentAttempt.builder()
                .enrollment(enrollment)
                .finalAssessment(finalAssessment)
                .score(score)
                .passed(score >= passMark)
                .attemptedAt(LocalDateTime.now())
                .build();

        finalAssessmentAttemptRepository.save(attempt);

        if (attempt.isPassed()) {
            enrollment.setStatus(EnrollmentStatus.COMPLETED);
            enrollmentRepository.save(enrollment);
        }

        return attempt;
    }

    // ------------------------------------------------
    // FETCH ALL ATTEMPTS (for dashboard)
    // ------------------------------------------------
    @Transactional(readOnly = true)
    public List<FinalAssessmentAttempt> getAttempts(String enrollmentId) {
        return finalAssessmentAttemptRepository
                .findByEnrollment_EnrollmentId(enrollmentId);
    }

    // ------------------------------------------------
    // RESET FINAL ASSESSMENT (re-enrollment)
    // ------------------------------------------------
    public void resetFinalAssessmentAttempts(String enrollmentId) {

        finalAssessmentAttemptRepository
                .deleteByEnrollment_EnrollmentId(enrollmentId);

        CourseEnrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        enrollment.setStatus(EnrollmentStatus.IN_PROGRESS);
        enrollmentRepository.save(enrollment);
    }
}
