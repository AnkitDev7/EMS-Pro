package com.example.Ems_Pro.Service;

import com.example.Ems_Pro.Entity.DepartmentLocationEntity;
import com.example.Ems_Pro.Payload.Request.DepartmentLocationPayload;
import com.example.Ems_Pro.Payload.Response.DepartmentLocationResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DepartmentLocationService {

    DepartmentLocationEntity saveDepartmentLocation(@Valid DepartmentLocationPayload departmentLocationPayload);

    List<DepartmentLocationResponse> getLocationsByDepartment(String departmentId);

    List<DepartmentLocationResponse> getAllDepartmentLocations();
}
