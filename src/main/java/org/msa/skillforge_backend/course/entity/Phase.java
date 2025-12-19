package org.msa.skillforge_backend.course.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.msa.skillforge_backend.assessment.entity.Test;

import java.util.List;
@Entity
@Table(name = "phase")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Phase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String phaseId;

    @NotBlank(message = "Phase title cannot be empty")
    private String title;

    @NotNull(message = "Phase must belong to a course")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "phase", cascade = CascadeType.ALL)
    private List<Content> contentsList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "test_id", unique = true)
    private Test test;
}

