package org.msa.skillforge_backend.auth.dto;

import lombok.*;

@Getter @Setter @AllArgsConstructor
public class AuthResponse {
    private String token;
    private String role;
}
