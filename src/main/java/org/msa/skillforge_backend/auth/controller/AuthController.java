package org.msa.skillforge_backend.auth.controller;

import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.auth.dto.AuthResponse;
import org.msa.skillforge_backend.auth.dto.LoginRequest;
import org.msa.skillforge_backend.auth.dto.RegisterRequest;
import org.msa.skillforge_backend.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        AuthResponse resp = authService.register(request);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse resp = authService.login(request);
        return ResponseEntity.ok(resp);
    }
}
