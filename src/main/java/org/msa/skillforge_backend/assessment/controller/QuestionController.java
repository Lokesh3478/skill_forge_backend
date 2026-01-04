package org.msa.skillforge_backend.assessment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.assessment.dto.question.MCQCreateRequest;
import org.msa.skillforge_backend.assessment.dto.question.MCQResponse;
import org.msa.skillforge_backend.assessment.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    /* ---------------- READ ---------------- */

    @GetMapping("/assessment/{assessmentId}")
    public List<MCQResponse> getQuestionsByAssessment(
            @PathVariable String assessmentId
    ) {
        return questionService.getQuestionsByAssessment(assessmentId);
    }
}
