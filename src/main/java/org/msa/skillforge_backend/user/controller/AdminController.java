package org.msa.skillforge_backend.user.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

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
