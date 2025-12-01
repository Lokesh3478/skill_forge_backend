package org.msa.skillforge_backend.user.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "Student Dashboard Accessed";
    }

    @GetMapping("/profile")
    public String profile() {
        return "Student Profile Data";
    }
}
