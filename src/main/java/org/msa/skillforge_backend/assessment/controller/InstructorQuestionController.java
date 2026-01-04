package org.msa.skillforge_backend.assessment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.assessment.dto.question.MCQCreateRequest;
import org.msa.skillforge_backend.assessment.dto.question.MCQResponse;
import org.msa.skillforge_backend.assessment.dto.question.MCQUpdateRequest;
import org.msa.skillforge_backend.assessment.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/instructor/mcq")
@RequiredArgsConstructor
public class InstructorQuestionController {
    private final QuestionService questionService;

    /* ---------------- CREATE MCQ ---------------- */

    @PostMapping("/create-mcq")
    @ResponseStatus(HttpStatus.CREATED)
    public MCQResponse createMCQ(
            @Valid @RequestBody MCQCreateRequest request
    ) {
        return questionService.createMCQ(request);
    }

    @PutMapping("/update-mcq")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMCQ(
            @Valid @RequestBody MCQUpdateRequest request
    ){
        questionService.updateMCQ(request);
    }

    @DeleteMapping("/delete-mcq/{questionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestion(
            @PathVariable String questionId
    ) {
        questionService.deleteQuestion(questionId);
    }
}
