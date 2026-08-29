package com.example.Ems_Pro.Controller;
import com.example.Ems_Pro.Entity.DepartmentEntity;
import com.example.Ems_Pro.Payload.Request.DepartmentPayload;
import com.example.Ems_Pro.Service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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
    @PutMapping
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

    @DeleteMapping("/{departmentId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> singleDeleteDepartment(@PathVariable String  departmentId) {

        DepartmentEntity departmentEntity = departmentService.deleteSingleDepartment(departmentId);

        if (departmentEntity != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Map.of("Message",
                            "Department deleted successfully")
                    );
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department Not Found");
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> readAllDepartments() {

        List<DepartmentEntity> departmentEntity = departmentService.readAllDepartment();

        if (departmentEntity != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Map.of("Message : ","Departments read successfully",
                            "Department List : ",departmentEntity
                    ));
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department Not Found");
    }
}
