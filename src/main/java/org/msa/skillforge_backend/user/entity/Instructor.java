package org.msa.skillforge_backend.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.msa.skillforge_backend.course.entity.Course;

import java.util.Set;

@Entity
@Table(name = "instructors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Instructor {

    @Id
    private String id;
    @OneToOne
    @MapsId
    @JoinColumn(name="id")
    private User user;
    @ManyToMany
    @JoinTable(
            name="course_taught",
            joinColumns = @JoinColumn(name="instructor_id"),
            inverseJoinColumns = @JoinColumn(name="course_id")
    )
    Set<Course> courseSet;
    //should add dept details and course uploaded details
}
