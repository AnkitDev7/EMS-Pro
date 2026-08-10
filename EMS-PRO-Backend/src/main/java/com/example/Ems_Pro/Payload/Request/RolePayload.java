package com.example.Ems_Pro.Payload.Request;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePayload {

    @NotBlank(message = "Role ID is required")
    private String roleId;

    @NotBlank(message = "Role name is required")
    private String roleName;

}
