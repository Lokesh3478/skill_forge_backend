package org.msa.skillforge_backend.auth.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String id;
    private String fullName;
    private String email;
    private String password;
    private String phone;
}
