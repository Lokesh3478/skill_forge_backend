package org.msa.skillforge_backend.assessment.controller;

import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.assessment.dto.assessment.AssessmentResponse;
import org.msa.skillforge_backend.assessment.dto.assessment.FinalAssessmentCreateRequest;
import org.msa.skillforge_backend.assessment.dto.assessment.TestCreateRequest;
import org.msa.skillforge_backend.assessment.service.AssessmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/instructor/assessment")
public class InstructorAssessmentController {
    private final AssessmentService assessmentService;

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

    /*------------------CREATE TEST*---------------------*/

    @PostMapping("/create-test")
    public ResponseEntity<AssessmentResponse> createTestAssessmentForCourse(
            @RequestBody TestCreateRequest testCreateRequest
    ){
        return new ResponseEntity<>(
                assessmentService.createTestForPhase(testCreateRequest),
                HttpStatus.CREATED
        );
    }

    /* ---------------- DELETE ---------------- */

    @DeleteMapping("/{assessmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAssessment(
            @PathVariable String assessmentId
    ) {
        assessmentService.deleteAssessment(assessmentId);
    }
}
