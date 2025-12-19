package org.msa.skillforge_backend.course.service;

import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.course.dto.*;
import org.msa.skillforge_backend.course.entity.Course;
import org.msa.skillforge_backend.course.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    /* ---------------- CREATE ---------------- */

    public CourseResponse createCourse(CourseCreateRequest request) {

        Course course = Course.builder()
                .courseName(request.courseName())
                .build();

        return mapToResponse(courseRepository.save(course));
    }

    /* ---------------- READ ---------------- */

    public CourseResponse getCourseById(String courseId) {
        return mapToResponse(
                courseRepository.findById(courseId)
                        .orElseThrow(() -> new NoSuchElementException("Course not found"))
        );
    }

    public List<CourseSummary> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(course -> new CourseSummary(
                        course.getCourseId(),
                        course.getCourseName()
                ))
                .toList();
    }

    /* ---------------- UPDATE ---------------- */

    public CourseResponse updateCourse(
            String courseId,
            CourseUpdateRequest request
    ) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));

        course.setCourseName(request.courseName());

        return mapToResponse(courseRepository.save(course));
    }

    /* ---------------- DELETE ---------------- */

    public void deleteCourse(String courseId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));

        courseRepository.delete(course);
    }

    /* ---------------- MAPPER ---------------- */

    private CourseResponse mapToResponse(Course course) {

        List<PhaseSummary> phaseSummaries =
                course.getPhases() == null
                        ? List.of()
                        : course.getPhases()
                        .stream()
                        .map(phase -> new PhaseSummary(
                                phase.getPhaseId(),
                                phase.getTitle()
                        ))
                        .toList();

        return new CourseResponse(
                course.getCourseId(),
                course.getCourseName(),
                phaseSummaries,
                course.getFinalAssessment() != null
                        ? course.getFinalAssessment().getAssessmentId()
                        : null
        );
    }
}
