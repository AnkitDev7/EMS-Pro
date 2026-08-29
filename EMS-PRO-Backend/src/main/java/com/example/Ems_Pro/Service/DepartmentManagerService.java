package com.example.Ems_Pro.Service;

import com.example.Ems_Pro.Payload.Request.DepartmentManagerPayload;
import com.example.Ems_Pro.Payload.Response.DepartmentManagerResponse;
import com.example.Ems_Pro.Payload.Response.DepartmentResponse;
import jakarta.validation.Valid;

public interface DepartmentManagerService {

    DepartmentManagerResponse addDepartmentManager(@Valid DepartmentManagerPayload departmentManagerPayload);

    DepartmentManagerResponse updateDepartmentManager(@Valid DepartmentManagerPayload departmentManagerPayload);

    void deleteDepartmentManager(String departmentId);
}
