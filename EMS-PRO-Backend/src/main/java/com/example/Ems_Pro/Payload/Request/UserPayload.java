package com.example.Ems_Pro.Payload.Request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPayload {

    @NotBlank(message = "User ID is required")
    @Size(max = 50, message = "User ID must not exceed 50 characters")
    private String userId;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Pattern(
            regexp = "^[a-zA-Z ]+$",
            message = "Name can contain only letters and spaces"
    )
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 150, message = "Email must not exceed 150 characters")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    private String password;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[6-9][0-9]{9}$",
            message = "Phone number must be a valid 10-digit Indian mobile number"
    )
    private String phoneNumber;

    @NotNull(message = "Role ID is required")
    @Positive(message = "Role ID must be greater than 0")
    private String roleId;

    @NotBlank(message = "Company ID is required")
    @Size(max = 50, message = "Company ID must not exceed 50 characters")
    private String companyId;

    @NotNull(message = "Location ID is required")
    @Positive(message = "Location ID must be greater than 0")
    private Integer locationId;

    @Positive(message = "Department ID must be greater than 0")
    private String departmentId;

/*
    @Size(max = 50, message = "Manager ID must not exceed 50 characters")
    private String managerId;

    @Size(max = 50, message = "Team Leader ID must not exceed 50 characters")
    private String teamLeaderId;
*/

    @Pattern(
            regexp = "ACTIVE|INACTIVE",
            message = "Status must be either ACTIVE or INACTIVE"
    )
    private String status;

    private MultipartFile profileImage;
}