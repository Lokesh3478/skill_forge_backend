package org.msa.skillforge_backend.course.service;

import lombok.AllArgsConstructor;
import org.msa.skillforge_backend.course.entity.Course;
import org.msa.skillforge_backend.course.exception.CourseAlreadyExisitsException;
import org.msa.skillforge_backend.course.exception.InvalidCourseDetails;
import org.msa.skillforge_backend.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course getCourse(String courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));
    }

    public Course updateCourse(String courseId, Course newCourse) {

        Course oldCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));

        oldCourse.setCourseName(newCourse.getCourseName());
        oldCourse.setInstructors(newCourse.getInstructors());

        return courseRepository.save(oldCourse);
    }

    public void deleteCourse(String courseId) {
        Course oldCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));
        courseRepository.delete(oldCourse);
    }
}
