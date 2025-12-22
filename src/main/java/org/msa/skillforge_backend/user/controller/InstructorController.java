package org.msa.skillforge_backend.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.assessment.dto.AssessmentResponse;
import org.msa.skillforge_backend.assessment.dto.FinalAssessmentCreateRequest;
import org.msa.skillforge_backend.assessment.dto.MCQCreateRequest;
import org.msa.skillforge_backend.assessment.dto.MCQResponse;
import org.msa.skillforge_backend.assessment.service.AssessmentService;
import org.msa.skillforge_backend.assessment.service.QuestionService;
import org.msa.skillforge_backend.course.dto.ContentResponse;
import org.msa.skillforge_backend.course.dto.PhaseCreateRequest;
import org.msa.skillforge_backend.course.dto.PhaseResponse;
import org.msa.skillforge_backend.course.dto.courseDto.CourseSummary;
import org.msa.skillforge_backend.course.service.ContentService;
import org.msa.skillforge_backend.course.service.CourseService;
import org.msa.skillforge_backend.course.service.PhaseService;
import org.msa.skillforge_backend.user.entity.User;
import org.msa.skillforge_backend.user.repository.InstructorRepository;
import org.msa.skillforge_backend.user.service.InstructorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/instructor")
public class InstructorController {

    private final InstructorService instructorService;
    private final CourseService courseService;
    private final ContentService contentService;
    private final PhaseService phaseService;
    private final QuestionService questionService;
    private final AssessmentService assessmentService;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "Instructor Dashboard Accessed";
    }

    @GetMapping("/my-courses")
    public List<CourseSummary> getMyCourses(Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        String userId = user.getId();   // ← THIS is your instructor ID

        return instructorService.getCoursesByInstructorId(userId);
    }

    /* ---------------- CREATE PHASE ---------------- */

    @PostMapping("/create-phase")
    @ResponseStatus(HttpStatus.CREATED)
    public PhaseResponse createPhase(@Valid @RequestBody PhaseCreateRequest request) {
        return phaseService.createPhase(request);
    }

    /* ---------------- CREATE CONTENT ---------------- */

    @PostMapping("/create-content")
    @ResponseStatus(HttpStatus.CREATED)
    public ContentResponse createContent(
            @RequestParam String phaseId,
            @RequestParam String contentName,
            @RequestParam String contentUrl
    ) {
        return contentService.createContent(contentName, contentUrl, phaseId);
    }

    /* ---------------- CREATE FINAL ASSESSMENT (Course-level) ---------------- */

    @PostMapping("/create-final-assessment")
    public ResponseEntity<AssessmentResponse> createFinalAssessmentForCourse(
            @RequestBody FinalAssessmentCreateRequest request
            ) {
        return new ResponseEntity<>(
                assessmentService.createFinalAssessmentForCourse(request),
                HttpStatus.CREATED
        );
    }

    /* ---------------- CREATE MCQ ---------------- */

    @PostMapping("/create-mcq")
    @ResponseStatus(HttpStatus.CREATED)
    public MCQResponse createMCQ(
            @Valid @RequestBody MCQCreateRequest request
    ) {
        return questionService.createMCQ(request);
    }

}
