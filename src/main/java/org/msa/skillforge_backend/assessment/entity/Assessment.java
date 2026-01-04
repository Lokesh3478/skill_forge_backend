package org.msa.skillforge_backend.assessment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="assessment")
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
public abstract class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String assessmentId;
    @OneToMany(
            mappedBy = "assessment",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @ToString.Exclude
    List<Question> questionList = new ArrayList<>();
    @NotNull
    @Min(1)
    @Max(300)
    @Column(nullable = false)
    private Integer durationInMinutes;
}
