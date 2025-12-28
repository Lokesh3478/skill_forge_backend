package org.msa.skillforge_backend.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.course.dto.courseDto.AssignInstructor;
import org.msa.skillforge_backend.course.dto.courseDto.CourseCreateRequest;
import org.msa.skillforge_backend.course.dto.courseDto.CourseResponse;
import org.msa.skillforge_backend.course.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final CourseService courseService;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "Admin Dashboard Accessed";
    }

    @PostMapping("/create-instructor")
    public String createInstructor() {
        return "Instructor Account Created (Example Endpoint)";
    }

    @GetMapping("/all-users")
    public String allUsers() {
        return "Admin Access: All Users List";
    }
}
