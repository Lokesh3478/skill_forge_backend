package org.msa.skillforge_backend.course.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.course.dto.CourseCreateRequest;
import org.msa.skillforge_backend.course.dto.CourseResponse;
import org.msa.skillforge_backend.course.dto.CourseSummary;
import org.msa.skillforge_backend.course.dto.CourseUpdateRequest;
import org.msa.skillforge_backend.course.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    /* ---------------- CREATE ---------------- */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse createCourse(
            @Valid @RequestBody CourseCreateRequest request
    ) {
        System.out.println("hit");
        System.out.println(request);
        return courseService.createCourse(request);
    }

    /* ---------------- READ ---------------- */

    @GetMapping("/{courseId}")
    public CourseResponse getCourseById(
            @PathVariable String courseId
    ) {

        return courseService.getCourseById(courseId);
    }

    /**
     * Optimized listing endpoint
     * Returns lightweight course data
     */
    @GetMapping
    public List<CourseSummary> getAllCourses() {
        return courseService.getAllCourses();
    }

    /* ---------------- UPDATE ---------------- */

    @PutMapping("/{courseId}")
    public CourseResponse updateCourse(
            @PathVariable String courseId,
            @Valid @RequestBody CourseUpdateRequest request
    ) {
        return courseService.updateCourse(courseId, request);
    }

    /* ---------------- DELETE ---------------- */

    @DeleteMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(
            @PathVariable String courseId
    ) {
        courseService.deleteCourse(courseId);
    }
}
