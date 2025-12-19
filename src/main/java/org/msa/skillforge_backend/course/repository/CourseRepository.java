package org.msa.skillforge_backend.course.repository;

import org.msa.skillforge_backend.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, String> {

    List<Course> findByCourseNameContainingIgnoreCase(String courseName);

    boolean existsByCourseId(String courseId);
}
