package org.msa.skillforge_backend.user.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/instructor")
public class InstructorController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "Instructor Dashboard Accessed";
    }

    @PostMapping("/create-course")
    public String createCourse() {
        return "Instructor Creates Course (Example Endpoint)";
    }

    @GetMapping("/my-courses")
    public String getCourses() {
        return "Instructor Course List";
    }
}
