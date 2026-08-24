package com.example.Ems_Pro.Payload.Request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentLocationPayload {

    @NotBlank(message = "Department ID is required")
    private String departmentId;

    @NotNull(message = "Location ID is required")
    private Integer locationId;
}
