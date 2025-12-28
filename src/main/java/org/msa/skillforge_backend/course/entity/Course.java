package org.msa.skillforge_backend.course.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.msa.skillforge_backend.assessment.entity.FinalAssessment;
import org.msa.skillforge_backend.taxonomy.entity.Topic;
import org.msa.skillforge_backend.user.entity.Instructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="course")
public class Course {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        String courseId;

        @NotBlank(message = "course name cannnot be empty")
        @Size(min=10,max=100,message="course name should range between 10 to 100 characters")
        String courseName;

        @ManyToMany(
                mappedBy = "courseSet"
        )
        Set<Instructor> instructorSet;

        @OneToMany(mappedBy = "course",cascade = CascadeType.ALL,orphanRemoval = true)
        List<Phase> phases;

        @OneToOne(mappedBy = "course", cascade = CascadeType.ALL,orphanRemoval = true)
        private FinalAssessment finalAssessment;

        @ManyToMany
        @JoinTable(
                name = "course_topic",
                joinColumns = @JoinColumn(name = "course_id"),
                inverseJoinColumns = @JoinColumn(name = "topic_id")
        )
        private Set<Topic> topics = new HashSet<>();

}