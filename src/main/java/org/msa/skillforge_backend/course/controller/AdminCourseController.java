package org.msa.skillforge_backend.course.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.course.dto.courseDto.AssignInstructor;
import org.msa.skillforge_backend.course.dto.courseDto.AssignTopicsRequest;
import org.msa.skillforge_backend.course.dto.courseDto.CourseCreateRequest;
import org.msa.skillforge_backend.course.dto.courseDto.CourseResponse;
import org.msa.skillforge_backend.course.entity.Course;
import org.msa.skillforge_backend.course.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/admin/courses")
@RequiredArgsConstructor
public class AdminCourseController {

    private final CourseService courseService;

    /* ---------------- CREATE COURSE ---------------- */

    @PostMapping("/create-course")
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse createCourse(
            @Valid @RequestBody CourseCreateRequest request
    ) {
        System.out.println("hit");
        System.out.println(request);
        return courseService.createCourse(request);
    }

    /*------------ADD INSTRUCTOR---------------*/

    @PostMapping("/add-instructor")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addInstructor(
            @Valid @RequestBody AssignInstructor assignInstructor
    ){
        courseService.assignInstructorToCourse(assignInstructor.courseId(),assignInstructor.instructorEmail());
    }

    @PutMapping("/{courseId}/topics")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCourseTopics(
            @PathVariable String courseId,
            @RequestBody AssignTopicsRequest request
    ) {
        courseService.updateCourseTopics(courseId, request.topicIds());
    }

}
