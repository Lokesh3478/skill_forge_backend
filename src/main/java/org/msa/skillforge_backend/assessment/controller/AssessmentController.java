package org.msa.skillforge_backend.assessment.controller;

import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.assessment.dto.assessment.AssessmentResponse;
import org.msa.skillforge_backend.assessment.dto.assessment.AssessmentWithQuestionsResponse;
import org.msa.skillforge_backend.assessment.service.AssessmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/assessments")
@RequiredArgsConstructor
public class AssessmentController {

    private final AssessmentService assessmentService;



    /* ---------------- READ ---------------- */

    @GetMapping("/{assessmentId}")
    public ResponseEntity<AssessmentResponse> getAssessmentById(
            @PathVariable String assessmentId
    ) {
        return ResponseEntity.ok(
                assessmentService.getAssessmentById(assessmentId)
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

    @GetMapping("/{assessmentId}/questions")
    public ResponseEntity<AssessmentWithQuestionsResponse> getAssessmentWithQuestions(
            @PathVariable String assessmentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                assessmentService.getAssessmentWithQuestions(
                        assessmentId,
                        org.springframework.data.domain.PageRequest.of(page, size)
                )
        );
    }

}

