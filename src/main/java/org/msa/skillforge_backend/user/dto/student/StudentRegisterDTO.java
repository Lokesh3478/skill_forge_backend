package org.msa.skillforge_backend.user.dto.student;

import lombok.Data;

@Data
public class StudentRegisterDTO {
    private String studentId;
    private String studentName;
    private String mailId;
    private String mobileNumber;
    private String password;
}
