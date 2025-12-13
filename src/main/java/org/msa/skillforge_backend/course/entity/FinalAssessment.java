package org.msa.skillforge_backend.course.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="final_assessment")
public class FinalAssessment extends Assessment{
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", unique = true)
    private Course course;
}
