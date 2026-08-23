package com.example.Ems_Pro.Controller;
import com.example.Ems_Pro.Entity.UsersEntity;
import com.example.Ems_Pro.Payload.Request.UserPayload;
import com.example.Ems_Pro.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/super_admin/users")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class UsersController {

    @Autowired
    private UserService userService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createUser(
            @RequestPart("data") String data,
            @RequestPart(
                    value = "profileImage",
                    required = false
            ) MultipartFile profileImage
    ) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        UserPayload userPayload =
                objectMapper.readValue(
                        data,
                        UserPayload.class
                );

        UsersEntity user =
                userService.createUser(
                        userPayload,
                        profileImage
                );

        return ResponseEntity
                .status(201)
                .body("User Created Successfully");
    }


    @PutMapping(
            path = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> updateUser(
            @PathVariable String id,
            @RequestPart("data") String data,
            @RequestPart(
                    value = "profileImage",
                    required = false
            ) MultipartFile profileImage
    ) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        UserPayload userPayload =
                objectMapper.readValue(
                        data,
                        UserPayload.class
                );

        userService.updateUser(
                id,
                userPayload,
                profileImage
        );

        return ResponseEntity.ok(
                "User Updated Successfully"
        );
    }
}