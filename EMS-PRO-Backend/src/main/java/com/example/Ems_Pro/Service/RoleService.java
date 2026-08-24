package com.example.Ems_Pro.Service;
import com.example.Ems_Pro.Entity.RoleEntity;
import com.example.Ems_Pro.Payload.Request.RolePayload;
import com.example.Ems_Pro.Payload.Response.RoleResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface RoleService {
    RoleEntity saveRole(@Valid RolePayload rolePayload);
    RoleEntity deleteRole(String id);
    RoleEntity updateRole(String id, @Valid RolePayload rolePayload);
    RoleEntity readRoleById(String id);
    List<RoleResponse> readAllRole();
}
