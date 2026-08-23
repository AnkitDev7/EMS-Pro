package com.example.Ems_Pro.Payload.Request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentPayload {

    @NotBlank(message = "Department ID is required")
    @Size(max = 20, message = "Department ID must not exceed 20 characters")
    private String departmentId;

    @NotBlank(message = "Department name is required")
    @Size(min = 2, max = 100,
            message = "Department name must be between 2 and 100 characters")
    private String departmentName;

    @NotBlank(message = "Company ID is required")
    private String companyId;

    @NotNull(message = "Active status is required")
    private String Status;
}
