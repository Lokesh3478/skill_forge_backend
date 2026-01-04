package org.msa.skillforge_backend.course_enrollment.service;

import lombok.RequiredArgsConstructor;
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
import org.msa.skillforge_backend.course_enrollment.entity.ContentProgress;
import org.msa.skillforge_backend.course_enrollment.entity.CourseEnrollment;
import org.msa.skillforge_backend.course_enrollment.entity.EnrollmentStatus;
import org.msa.skillforge_backend.course_enrollment.entity.PhaseProgress;
import org.msa.skillforge_backend.course_enrollment.repository.*;
import org.msa.skillforge_backend.user.entity.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseEnrollmentService {

    private final CourseEnrollmentRepository enrollmentRepository;
    private final PhaseProgressRepository phaseProgressRepository;
    private final ContentProgressRepository contentProgressRepository;
    private final PhaseTestAttemptRepository phaseTestAttemptRepository;
    private final FinalAssessmentAttemptRepository finalAssessmentAttemptRepository;
    private final CourseRepository courseRepository;
    private final PhaseRepository phaseRepository;
    private final ContentRepository contentRepository;

    // ----------------------------
    // ENROLL STUDENT
    // ----------------------------
    public CourseEnrollmentResponseDTO enrollStudent(String studentId, String courseId) {
        // Check if enrollment exists
        CourseEnrollment enrollment = enrollmentRepository
                .findByStudent_IdAndCourse_CourseId(studentId, courseId)
                .orElseGet(() -> {
                    // Create new enrollment
                    Course course = courseRepository.findById(courseId)
                            .orElseThrow(() -> new NoSuchElementException("Course not found"));
                    CourseEnrollment newEnrollment = CourseEnrollment.builder()
                            .student(new Student(studentId, null)) // Assuming user already exists
                            .course(course)
                            .enrolledAt(LocalDateTime.now())
                            .status(EnrollmentStatus.IN_PROGRESS)
                            .build();
                    return enrollmentRepository.save(newEnrollment);
                });

        // If previously dropped, reset
        if (enrollment.getStatus() == EnrollmentStatus.DROPPED) {
            resetEnrollmentProgress(enrollment);
            enrollment.setStatus(EnrollmentStatus.IN_PROGRESS);
            enrollment.setEnrolledAt(LocalDateTime.now());
        }

        return mapToResponseDTO(enrollment);
    }

    // ----------------------------
    // DROP COURSE
    // ----------------------------
    public CourseEnrollmentResponseDTO dropCourse(String enrollmentId) {
        CourseEnrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        enrollment.setStatus(EnrollmentStatus.DROPPED);
        return mapToResponseDTO(enrollment);
    }

    // ----------------------------
    // RE-ENROLL (start over)
    // ----------------------------
    public CourseEnrollmentResponseDTO reEnroll(String enrollmentId) {
        CourseEnrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        if (enrollment.getStatus() != EnrollmentStatus.DROPPED) {
            throw new RuntimeException("Enrollment is active or completed, cannot re-enroll");
        }

        resetEnrollmentProgress(enrollment);
        enrollment.setStatus(EnrollmentStatus.IN_PROGRESS);
        enrollment.setEnrolledAt(LocalDateTime.now());

        return mapToResponseDTO(enrollment);
    }

    // ----------------------------
    // GET ENROLLMENT DETAIL
    // ----------------------------
    public CourseEnrollmentDetailDTO getEnrollmentDetail(String enrollmentId) {
        CourseEnrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        double courseCompletion = getCourseCompletionPercentage(enrollmentId);

        List<PhaseProgressDTO> phases = enrollment.getPhaseProgresses()
                .stream()
                .map(pp -> {
                    double phaseCompletion = getPhaseCompletionPercentage(pp.getEnrollment().getEnrollmentId(),
                            pp.getPhase().getPhaseId());
                    return PhaseProgressDTO.builder()
                            .phaseId(pp.getPhase().getPhaseId())
                            .phaseName(pp.getPhase().getTitle())
                            .completionPercentage(phaseCompletion)
                            .phaseTestPassed(pp.isPhaseTestPassed())
                            .build();
                })
                .toList();

        return CourseEnrollmentDetailDTO.builder()
                .enrollmentId(enrollment.getEnrollmentId())
                .courseId(enrollment.getCourse().getCourseId())
                .courseName(enrollment.getCourse().getCourseName())
                .status(enrollment.getStatus())
                .enrolledAt(enrollment.getEnrolledAt())
                .courseCompletionPercentage(courseCompletion)
                .phases(phases)
                .build();
    }

    // ----------------------------
    // GET STUDENT ENROLLMENTS
    // ----------------------------
    public List<CourseEnrollmentListDTO> getStudentEnrollments(String studentId) {
        List<CourseEnrollment> enrollments = enrollmentRepository.findByStudent_Id(studentId);

        return enrollments.stream()
                .map(e -> CourseEnrollmentListDTO.builder()
                        .enrollmentId(e.getEnrollmentId())
                        .courseId(e.getCourse().getCourseId())
                        .courseName(e.getCourse().getCourseName())
                        .status(e.getStatus())
                        .completionPercentage(getCourseCompletionPercentage(e.getEnrollmentId()))
                        .build())
                .toList();
    }

    // ----------------------------
    // COURSE COMPLETION PERCENTAGE
    // ----------------------------
    public double getCourseCompletionPercentage(String enrollmentId) {
        List<PhaseProgress> phases = phaseProgressRepository.findAllByEnrollment(enrollmentId);
        if (phases.isEmpty()) return 0.0;

        double total = 0;
        for (PhaseProgress pp : phases) {
            total += getPhaseCompletionPercentage(enrollmentId, pp.getPhase().getPhaseId());
        }
        return total / phases.size();
    }

    // ----------------------------
    // PHASE COMPLETION PERCENTAGE (seconds-based)
    // ----------------------------
    private double getPhaseCompletionPercentage(String enrollmentId, String phaseId) {
        List<Content> contents = contentRepository.findByPhase_PhaseId(phaseId);
        if (contents.isEmpty()) return 0.0;

        long totalDuration = contents.stream().mapToLong(Content::getDurationInSeconds).sum();
        if (totalDuration == 0) return 0.0;

        long totalSpent = contents.stream()
                .mapToLong(c -> contentProgressRepository
                        .findByEnrollment_EnrollmentIdAndContent_ContentId(enrollmentId, c.getContentId())
                        .map(ContentProgress::getSecondsSpent)
                        .orElse(0L))
                .sum();

        return Math.min(100.0, (totalSpent * 100.0) / totalDuration);
    }

    // ----------------------------
    // FINAL ASSESSMENT ELIGIBILITY
    // ----------------------------
    public boolean isFinalAssessmentEligible(String enrollmentId) {
        long unpassedPhases = phaseProgressRepository.countUnpassedPhases(enrollmentId);
        return unpassedPhases == 0;
    }

    // ----------------------------
    // RESET ENROLLMENT PROGRESS (for re-enroll)
    // ----------------------------
    private void resetEnrollmentProgress(CourseEnrollment enrollment) {
        String enrollmentId = enrollment.getEnrollmentId();

        contentProgressRepository.deleteByEnrollment(enrollmentId);
        phaseTestAttemptRepository.deleteByEnrollment(enrollmentId);
        phaseProgressRepository.findAllByEnrollment(enrollmentId)
                .forEach(pp -> pp.setPhaseTestPassed(false));
        finalAssessmentAttemptRepository.deleteByEnrollment(enrollmentId);
    }

    // ----------------------------
    // MAPPER TO RESPONSE DTO
    // ----------------------------
    private CourseEnrollmentResponseDTO mapToResponseDTO(CourseEnrollment enrollment) {
        return CourseEnrollmentResponseDTO.builder()
                .enrollmentId(enrollment.getEnrollmentId())
                .courseId(enrollment.getCourse().getCourseId())
                .courseName(enrollment.getCourse().getCourseName())
                .status(enrollment.getStatus())
                .enrolledAt(enrollment.getEnrolledAt())
                .build();
    }
}

