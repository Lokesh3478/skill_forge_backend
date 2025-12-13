package org.msa.skillforge_backend.course.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "phase")
public class Phase {
    @Id
    @GeneratedValue
    private UUID phaseId;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

    @OneToMany(mappedBy = "phase", cascade = CascadeType.ALL)
    private List<Content> contentsList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "test_id", unique = true)
    private Test test;
}

