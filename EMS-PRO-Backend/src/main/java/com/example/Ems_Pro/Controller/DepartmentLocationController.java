package com.example.Ems_Pro.Controller;
import com.example.Ems_Pro.Entity.DepartmentLocationEntity;
import com.example.Ems_Pro.Payload.Request.DepartmentLocationPayload;
import com.example.Ems_Pro.Payload.Response.DepartmentLocationResponse;
import com.example.Ems_Pro.Service.DepartmentLocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RequestMapping("/department/locations")
@PreAuthorize("hasRole('SUPER_ADMIN')")
@RestController
public class DepartmentLocationController {

    @Autowired
    private DepartmentLocationService departmentLocationService;

    @PostMapping
    public ResponseEntity<?> addDepartmentLocation(
            @Valid @RequestBody DepartmentLocationPayload  departmentLocationPayload){

      DepartmentLocationEntity departmentLocationEntity =
              departmentLocationService.saveDepartmentLocation(departmentLocationPayload);


      if(departmentLocationEntity!=null){
          return ResponseEntity
                  .status(HttpStatus.CREATED)
                  .body(Map.of("Message", "Department Location Added Successfully,",
                          "Data",departmentLocationEntity
                  ));
      }

      throw new RuntimeException("Department Location Not Added Successfully");

    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<?> getAllDepartmentLocation(String departmentId){

        List<DepartmentLocationResponse> departmentLocationEntity =
                departmentLocationService.getLocationsByDepartment(departmentId);

        return ResponseEntity.ok(departmentLocationEntity);
    }

    @GetMapping
    public ResponseEntity<?> getDepartmentLocation(){

      List<DepartmentLocationResponse> departmentLocationResponses =
              departmentLocationService.getAllDepartmentLocations();

      return ResponseEntity
              .status(HttpStatus.OK).body(Map.of("Message", "Department Location Successfully",
              "Data",departmentLocationResponses
      ));
    }


}
