package org.msa.skillforge_backend.course_enrollment.service;

import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.course.entity.Content;
import org.msa.skillforge_backend.course.entity.Phase;
import org.msa.skillforge_backend.course.repository.ContentRepository;
import org.msa.skillforge_backend.course.repository.PhaseRepository;
import org.msa.skillforge_backend.course_enrollment.dto.contentprogress.ContentProgressDTO;
import org.msa.skillforge_backend.course_enrollment.dto.phaseprogress.PhaseProgressDetailDTO;
import org.msa.skillforge_backend.course_enrollment.entity.ContentProgress;
import org.msa.skillforge_backend.course_enrollment.entity.CourseEnrollment;
import org.msa.skillforge_backend.course_enrollment.entity.PhaseProgress;
import org.msa.skillforge_backend.course_enrollment.entity.PhaseTestAttempt;
import org.msa.skillforge_backend.course_enrollment.repository.ContentProgressRepository;
import org.msa.skillforge_backend.course_enrollment.repository.CourseEnrollmentRepository;
import org.msa.skillforge_backend.course_enrollment.repository.PhaseProgressRepository;
import org.msa.skillforge_backend.course_enrollment.repository.PhaseTestAttemptRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PhaseProgressService {

    private final PhaseProgressRepository phaseProgressRepository;
    private final ContentProgressRepository contentProgressRepository;
    private final PhaseTestAttemptRepository phaseTestAttemptRepository;
    private final PhaseRepository phaseRepository;
    private final ContentRepository contentRepository;
    private final CourseEnrollmentRepository enrollmentRepository;

    // ------------------------------------------------
    // GET OR CREATE PHASE PROGRESS
    // ------------------------------------------------
    public PhaseProgress getOrCreatePhaseProgress(String enrollmentId, String phaseId) {

        return phaseProgressRepository
                .findByEnrollment_EnrollmentIdAndPhase_PhaseId(enrollmentId, phaseId)
                .orElseGet(() -> {
                    CourseEnrollment enrollment = enrollmentRepository.findById(enrollmentId)
                            .orElseThrow(() -> new RuntimeException("Enrollment not found"));

                    Phase phase = phaseRepository.findById(phaseId)
                            .orElseThrow(() -> new RuntimeException("Phase not found"));

                    PhaseProgress progress = PhaseProgress.builder()
                            .enrollment(enrollment)
                            .phase(phase)
                            .phaseTestPassed(false)
                            .build();

                    return phaseProgressRepository.save(progress);
                });
    }

    // ------------------------------------------------
    // UPDATE CONTENT WATCH TIME (seconds-based)
    // ------------------------------------------------
    public void updateContentProgress(
            String enrollmentId,
            String phaseId,
            String contentId,
            long secondsSpentIncrement
    ) {
        PhaseProgress phaseProgress = getOrCreatePhaseProgress(enrollmentId, phaseId);

        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("Content not found"));

        ContentProgress contentProgress = contentProgressRepository
                .findByEnrollment_EnrollmentIdAndContent_ContentId(enrollmentId, contentId)
                .orElseGet(() -> ContentProgress.builder()
                        .enrollment(phaseProgress.getEnrollment())
                        .content(content)
                        .secondsSpent(0L)
                        .build());

        long updatedSeconds = Math.min(
                content.getDurationInSeconds(),
                contentProgress.getSecondsSpent() + secondsSpentIncrement
        );

        contentProgress.setSecondsSpent(updatedSeconds);
        contentProgressRepository.save(contentProgress);
    }

    // ------------------------------------------------
    // PHASE COMPLETION PERCENTAGE
    // ------------------------------------------------
    public double getPhaseCompletionPercentage(String enrollmentId, String phaseId) {

        List<Content> contents = contentRepository.findByPhase_PhaseId(phaseId);
        if (contents.isEmpty()) return 0.0;

        long totalDuration = contents.stream()
                .mapToLong(Content::getDurationInSeconds)
                .sum();

        if (totalDuration == 0) return 0.0;

        long totalSpent = contents.stream()
                .mapToLong(content ->
                        contentProgressRepository
                                .findByEnrollment_EnrollmentIdAndContent_ContentId(enrollmentId, content.getContentId())
                                .map(ContentProgress::getSecondsSpent)
                                .orElse(0L))
                .sum();

        return Math.min(100.0, (totalSpent * 100.0) / totalDuration);
    }

    // ------------------------------------------------
    // CHECK IF PHASE TEST CAN BE ATTEMPTED
    // ------------------------------------------------
    public boolean canAttemptPhaseTest(String enrollmentId, String phaseId) {
        double completion = getPhaseCompletionPercentage(enrollmentId, phaseId);
        return completion >= 100.0;
    }

    // ------------------------------------------------
    // RECORD PHASE TEST ATTEMPT
    // ------------------------------------------------
    public PhaseTestAttempt recordPhaseTestAttempt(
            String enrollmentId,
            String phaseId,
            int score,
            int passMark
    ) {
        PhaseProgress phaseProgress = getOrCreatePhaseProgress(enrollmentId, phaseId);

        if (!canAttemptPhaseTest(enrollmentId, phaseId)) {
            throw new RuntimeException("Phase content not fully completed");
        }

        PhaseTestAttempt attempt = PhaseTestAttempt.builder()
                .phaseProgress(phaseProgress)
                .score(score)
                .passed(score >= passMark)
                .attemptedAt(LocalDateTime.now())
                .build();

        phaseTestAttemptRepository.save(attempt);

        if (attempt.isPassed()) {
            phaseProgress.setPhaseTestPassed(true);
            phaseProgressRepository.save(phaseProgress);
        }

        return attempt;
    }

    // ------------------------------------------------
    // GET PHASE PROGRESS DETAIL (for UI)
    // ------------------------------------------------
    public PhaseProgressDetailDTO getPhaseProgressDetail(String enrollmentId, String phaseId) {

        PhaseProgress phaseProgress = getOrCreatePhaseProgress(enrollmentId, phaseId);

        List<ContentProgressDTO> contentProgresses =
                contentRepository.findByPhase_PhaseId(phaseId).stream()
                        .map(content -> {
                            long spent = contentProgressRepository
                                    .findByEnrollment_EnrollmentIdAndContent_ContentId(enrollmentId, content.getContentId())
                                    .map(ContentProgress::getSecondsSpent)
                                    .orElse(0L);

                            double percentage = content.getDurationInSeconds() == 0
                                    ? 0
                                    : (spent * 100.0) / content.getDurationInSeconds();

                            return ContentProgressDTO.builder()
                                    .contentId(content.getContentId())
                                    .contentTitle(content.getContentName())
                                    .secondsSpent(spent)
                                    .completionPercentage(Math.min(100.0, percentage))
                                    .build();
                        })
                        .toList();

        return PhaseProgressDetailDTO.builder()
                .phaseId(phaseId)
                .phaseName(phaseProgress.getPhase().getTitle())
                .completionPercentage(getPhaseCompletionPercentage(enrollmentId, phaseId))
                .phaseTestPassed(phaseProgress.isPhaseTestPassed())
                .contents(contentProgresses)
                .build();
    }
}

