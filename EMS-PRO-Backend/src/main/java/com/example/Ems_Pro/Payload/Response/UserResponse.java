package com.example.Ems_Pro.Payload.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String userId;
    private LocalDateTime userCreatedAt;
    private String email;
    private String name;
    private String phoneNo;
    private String status;

    private CompanyResponse company;
    private LocationResponse location;
    private RoleResponse role;
    private DepartmentResponse department;

    private String profileImage;
}
