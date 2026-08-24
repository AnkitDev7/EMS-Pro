package com.example.Ems_Pro.Controller;
import com.example.Ems_Pro.Entity.DepartmentEntity;
import com.example.Ems_Pro.Payload.Request.DepartmentPayload;
import com.example.Ems_Pro.Service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/departments")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<?> createDepartment(
            @Valid @RequestBody DepartmentPayload departmentPayload) {

        DepartmentEntity departmentEntity =
                departmentService.createDepartment(departmentPayload);

        if (departmentEntity != null) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Department saved successfully");
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Department not saved successfully");
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> updateDepartment(@Valid @RequestBody DepartmentPayload departmentPayload) {

       DepartmentEntity departmentEntity = departmentService.updateDepartment(departmentPayload);

       if (departmentEntity != null) {
           return  ResponseEntity
                   .status(HttpStatus.OK)
                   .body(Map.of("Department Update successfully",departmentEntity)
                   );
       }

       throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department not Update successfully");
    }
}
