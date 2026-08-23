package com.example.Ems_Pro.Service;
import com.example.Ems_Pro.Entity.DepartmentEntity;
import com.example.Ems_Pro.Payload.Request.DepartmentPayload;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public interface DepartmentService {
    DepartmentEntity createDepartment(DepartmentPayload departmentPayload);

    DepartmentEntity updateDepartment(@Valid DepartmentPayload departmentPayload);
}
