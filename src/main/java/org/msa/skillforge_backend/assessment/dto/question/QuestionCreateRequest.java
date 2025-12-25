package org.msa.skillforge_backend.assessment.dto.question;

public interface QuestionCreateRequest {
    String assessmentId();
    String questionText();
    String type(); // "MCQ", future: "PASSAGE", "CODING"
}
