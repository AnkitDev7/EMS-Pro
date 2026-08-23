package com.example.Ems_Pro.Controller;

import com.example.Ems_Pro.Entity.CompanyEntity;
import com.example.Ems_Pro.Entity.UsersEntity;
import com.example.Ems_Pro.Payload.Request.CompanyPayload;
import com.example.Ems_Pro.Payload.Request.UserPayload;
import com.example.Ems_Pro.Service.CompanyService;
import com.example.Ems_Pro.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
//@RequestMapping("/super_admin")
//@PreAuthorize("hasRole('SUPER_ADMIN')")
@RequestMapping("/auth/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<?> createCompany(
            @Valid @RequestBody CompanyPayload companyPayload) {

        CompanyEntity companyEntity =
                companyService.createCompany(companyPayload);

        if (companyEntity != null) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of(
                            "message", "Company created successfully",
                            "company", companyEntity
                    ));
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "message", "Company creation failed"
                ));
    }

    // SUPER_ADMIN only
    @PutMapping("/{companyId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> updateCompany(
            @PathVariable String companyId,
            @Valid @RequestBody CompanyPayload payload) {

        CompanyEntity company =
                companyService.updateCompany(companyId, payload);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Company updated successfully",
                        "company", company
                )
        );
    }

    // SUPER_ADMIN only
    @DeleteMapping("/{companyId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> deleteCompany(
            @PathVariable String companyId) {

        companyService.deleteCompany(companyId);

        return ResponseEntity.ok(
                Map.of("message", "Company deleted successfully")
        );
    }
}
