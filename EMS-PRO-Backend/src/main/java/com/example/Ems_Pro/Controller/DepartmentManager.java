package com.example.Ems_Pro.Controller;

import com.example.Ems_Pro.Payload.Request.DepartmentManagerPayload;
import com.example.Ems_Pro.Payload.Response.DepartmentManagerResponse;
import com.example.Ems_Pro.Payload.Response.DepartmentResponse;
import com.example.Ems_Pro.Service.DepartmentManagerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/department_manager")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class DepartmentManager {

    @Autowired
    private DepartmentManagerService departmentManagerService;

    @PostMapping
    public ResponseEntity<?> addDepartmentManager(
            @Valid @RequestBody DepartmentManagerPayload departmentManagerPayload){

      DepartmentManagerResponse  departmentManagerResponse =
              departmentManagerService.addDepartmentManager(departmentManagerPayload);

      if (departmentManagerService != null){
          return ResponseEntity
                  .status(HttpStatus.CREATED)
                  .body(Map.of("departmentManagerResponse",
                          "Department Manager Created Successfully",
                  "DepartmentManagerResponse",departmentManagerResponse
                  ));
      }

      throw new RuntimeException("Department Manager Not Found");
    }

    @PutMapping
    public ResponseEntity<?> updateDepartmentManager(
            @Valid @RequestBody DepartmentManagerPayload departmentManagerPayload) {

        DepartmentManagerResponse departmentResponse =
                departmentManagerService.updateDepartmentManager(departmentManagerPayload);

        Map<String, Object> response = new HashMap<>();

        response.put("message", "Department manager updated successfully");
        response.put("status", 200);
        response.put("data", departmentResponse);

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteDepartmentManager(
            String departmentId) {

        departmentManagerService.deleteDepartmentManager(
                 departmentId
        );

        return ResponseEntity.ok("Department Manager deleted successfully");
    }

}
