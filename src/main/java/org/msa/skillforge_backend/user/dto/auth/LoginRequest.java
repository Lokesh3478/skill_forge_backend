package org.msa.skillforge_backend.user.dto.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String mail;
    private String password;
}
