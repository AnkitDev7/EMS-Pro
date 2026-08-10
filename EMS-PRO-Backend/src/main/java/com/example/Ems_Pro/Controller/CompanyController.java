package com.example.Ems_Pro.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/super_admin")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class CompanyController {

    @PostMapping("/user")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> saveUser(){

        return ResponseEntity.ok().build();
    }

}
