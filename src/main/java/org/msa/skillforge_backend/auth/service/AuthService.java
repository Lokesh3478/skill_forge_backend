package org.msa.skillforge_backend.auth.service;

import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.auth.dto.AuthResponse;
import org.msa.skillforge_backend.auth.dto.LoginRequest;
import org.msa.skillforge_backend.auth.dto.RegisterRequest;
import org.msa.skillforge_backend.auth.exception.InvalidCredentialsException;
import org.msa.skillforge_backend.auth.exception.UserAlreadyExistsException;
import org.msa.skillforge_backend.auth.security.JwtService;
import org.msa.skillforge_backend.user.entity.User;
import org.msa.skillforge_backend.user.entity.UserRole;
import org.msa.skillforge_backend.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        if (repo.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already in use");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(request.getRole())
                .build();

        repo.save(user);

        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token, user.getRole().name());
    }

    public AuthResponse login(LoginRequest request) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException ex) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        User user = repo.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token, user.getRole().name());
    }
}