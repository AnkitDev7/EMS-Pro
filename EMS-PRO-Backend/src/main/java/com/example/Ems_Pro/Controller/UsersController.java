package com.example.Ems_Pro.Controller;
import com.example.Ems_Pro.Entity.UsersEntity;
import com.example.Ems_Pro.Payload.Request.UserPayload;
import com.example.Ems_Pro.Payload.Response.UserResponse;
import com.example.Ems_Pro.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;


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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSingleUser(@PathVariable String id) {

      UsersEntity usersEntity =  userService.deleteSingleUser(id);

      if (usersEntity != null) {
          return ResponseEntity.ok("User Deleted Successfully");
      }

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {

     UserResponse userResponse =  userService.getSingleUser(id);

     if (userResponse != null) {
         return ResponseEntity.ok(userResponse);
     }
     return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");

    }


    @GetMapping
    public ResponseEntity<?> getAllUsers() {

        List<UserResponse> userResponse = userService.getAllUsers();

        if (userResponse != null) {

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Map.of(
                            "message", "All Users fetched successfully",
                            "roles", userResponse
                    ));

        };

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("All Users Not Found");
    }



}