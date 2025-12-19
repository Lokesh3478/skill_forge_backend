package org.msa.skillforge_backend.assessment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "mcq_question")
public class MCQQuestion extends Question {
    @ElementCollection
    private List<String> options;
    @Column(nullable = false)
    private String correctAnswer;
}
