package com.example.Ems_Pro.Controller;
import com.example.Ems_Pro.Entity.RoleEntity;
import com.example.Ems_Pro.Payload.Request.RolePayload;
import com.example.Ems_Pro.Service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/roles")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<?> saveRole(
            @Valid @RequestBody RolePayload rolePayload) {
        RoleEntity roleEntity = roleService.saveRole(rolePayload);
        return ResponseEntity.ok().body("Role saved successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable String id) {
        RoleEntity roleEntity = roleService.deleteRole(id);

        if (roleEntity != null) {
            return ResponseEntity.ok().body("Role deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Role not found");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable String id,
                                        @Valid @RequestBody RolePayload rolePayload) {
        RoleEntity roleEntity = roleService.updateRole(id, rolePayload);
        if (roleEntity != null) {
            return ResponseEntity.ok().body("Role updated successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Role not found");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readRoleById(@PathVariable String id) {

        RoleEntity roleEntity = roleService.readRoleById(id);

        if (roleEntity != null) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of
                    ("Your Data", roleEntity)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Role not found");
    }

    @GetMapping
    public ResponseEntity<?> readAllRoles() {

        List<RoleEntity> roleEntity = roleService.readAllRole();

        if (roleEntity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of
                    ("message", "Location not found")
            );
        }

        return ResponseEntity.ok().body(roleEntity);
    }


}