package org.msa.skillforge_backend.assessment.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.msa.skillforge_backend.course.entity.Course;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="final_assessment")
public class FinalAssessment extends Assessment {
    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id", unique = true)
    private Course course;
}
