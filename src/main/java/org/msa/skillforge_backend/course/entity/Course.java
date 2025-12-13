package org.msa.skillforge_backend.course.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.msa.skillforge_backend.user.entity.Instructor;

import java.util.List;

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
    List<Instructor> instructors;
    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
    List<Phase> phases;
    @OneToOne(mappedBy = "course", cascade = CascadeType.ALL)
    private FinalAssessment finalAssessment;
}
