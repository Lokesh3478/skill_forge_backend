package org.msa.skillforge_backend.course.controller;

import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.course.entity.Course;
import org.msa.skillforge_backend.course.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
}
