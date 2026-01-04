package org.msa.skillforge_backend.assessment.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "question")
public abstract class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String questionId;

    @Column(nullable = false)
    private String questionText;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assessment_id", nullable = false)
    private Assessment assessment;
}
