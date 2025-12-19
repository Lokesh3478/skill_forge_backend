package org.msa.skillforge_backend.assessment.dto;

public interface QuestionCreateRequest {
    String assessmentId();
    String questionText();
    String type(); // "MCQ", future: "PASSAGE", "CODING"
}
