package com.example.Ems_Pro.Payload.Request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentManagerPayload {

    @NotBlank(message = "Department ID can not be empty or Blank")
    private String departmentId;

    @NotBlank(message = "User ID can not be empty or Blank")
    private String userId;

    @NotNull(message = "Assigned Date can not be null")
    private LocalDate assignedDate;

    private LocalDate endDate;
}