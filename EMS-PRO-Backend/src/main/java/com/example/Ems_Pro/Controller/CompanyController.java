package com.example.Ems_Pro.Controller;

import com.example.Ems_Pro.Entity.UsersEntity;
import com.example.Ems_Pro.Payload.Request.UserPayload;
import com.example.Ems_Pro.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/super_admin")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class CompanyController {

}
