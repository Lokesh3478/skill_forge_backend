package org.msa.skillforge_backend.assessment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="assessment")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String assessmentId;
    @OneToMany(
            mappedBy = "assessment",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<Question> questionList = new ArrayList<>();
}
