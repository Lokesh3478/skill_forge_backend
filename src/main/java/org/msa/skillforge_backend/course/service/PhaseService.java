package org.msa.skillforge_backend.course.service;

import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.course.dto.content.ContentResponse;
import org.msa.skillforge_backend.course.dto.phase.PhaseCreateRequest;
import org.msa.skillforge_backend.course.dto.phase.PhaseResponse;
import org.msa.skillforge_backend.course.dto.phase.PhaseSummary;
import org.msa.skillforge_backend.course.entity.Content;
import org.msa.skillforge_backend.course.entity.Course;
import org.msa.skillforge_backend.course.entity.Phase;
import org.msa.skillforge_backend.course.repository.CourseRepository;
import org.msa.skillforge_backend.course.repository.PhaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PhaseService {

    private final PhaseRepository phaseRepository;
    private final CourseRepository courseRepository;

    /* ---------------- CREATE ---------------- */

    public PhaseResponse createPhase(PhaseCreateRequest request) {

        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new NoSuchElementException("Course not found"));

        if(phaseRepository.existsByCourse_CourseIdAndTitle(course.getCourseId(), request.title())) {
            throw new IllegalStateException("Phase already exists in this course");
        }

        Phase phase = Phase.builder()
                .title(request.title())
                .course(course)
                .build();

        return mapToResponse(phaseRepository.save(phase));
    }

    /* ---------------- READ ---------------- */

    public PhaseResponse getPhaseById(String phaseId) {
        return mapToResponse(
                phaseRepository.findById(phaseId)
                        .orElseThrow(() -> new NoSuchElementException("Phase not found"))
        );
    }

    public List<PhaseSummary> getPhasesByCourse(String courseId) {
        return phaseRepository.findByCourse_CourseId(courseId)
                .stream()
                .map(phase ->
                        new PhaseSummary(
                                phase.getPhaseId(),
                                phase.getTitle()
                        )
                )
                .toList();
    }
    /**/

    /* ---------------- UPDATE ---------------- */

    public PhaseResponse updatePhase(String phaseId, String title) {

        Phase phase = phaseRepository.findById(phaseId)
                .orElseThrow(() -> new NoSuchElementException("Phase not found"));

        phase.setTitle(title);

        return mapToResponse(phaseRepository.save(phase));
    }

    /* ---------------- DELETE ---------------- */

    public void deletePhase(String phaseId) {

        Phase phase = phaseRepository.findById(phaseId)
                .orElseThrow(() -> new NoSuchElementException("Phase not found"));

        phaseRepository.delete(phase);
    }

    /* ---------------- MAPPER ---------------- */

    private PhaseResponse mapToResponse(Phase phase) {

        List<ContentResponse> contents =
                phase.getContentsList() == null
                        ? List.of()
                        : phase.getContentsList()
                        .stream()
                        .map(this::mapContent)
                        .toList();

        return new PhaseResponse(
                phase.getPhaseId(),
                phase.getTitle(),
                phase.getCourse().getCourseId(),
                phase.getTest() != null ? phase.getTest().getAssessmentId() : null,
                contents
        );
    }

    private ContentResponse mapContent(Content content) {
        return new ContentResponse(
                content.getContentId(),
                content.getContentName(),
                content.getContentUrl(),
                content.getPhase().getPhaseId(),
                content.getDurationInSeconds()
        );
    }
}
