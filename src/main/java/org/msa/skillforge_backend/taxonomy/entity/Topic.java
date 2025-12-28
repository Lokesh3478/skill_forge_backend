package org.msa.skillforge_backend.taxonomy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import org.msa.skillforge_backend.course.entity.Course;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(
        name = "topic",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"domain_id", "name"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String topicId;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "domain_id")
    private Domain domain;

    @ManyToMany(mappedBy = "topics")
    private Set<Course> courses = new HashSet<>();

}
