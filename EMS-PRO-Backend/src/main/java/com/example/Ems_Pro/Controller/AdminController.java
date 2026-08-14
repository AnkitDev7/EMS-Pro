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
@RequestMapping("/admin")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;


    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createAdmin(
            @RequestPart("data")
            String data,

            @RequestPart(
                    value = "profileImage",
                    required = false
            )
            MultipartFile profileImage

    ) throws Exception {

        // JSON String -> UserPayload
        ObjectMapper objectMapper = new ObjectMapper();

        UserPayload userPayload =
                objectMapper.readValue(
                        data,
                        UserPayload.class
                );


        UsersEntity admin = userService.createAdmin(userPayload, profileImage);


        return ResponseEntity.ok("Admin Created Sucessfully");
    }


    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping(path = "/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAdmin(
            @PathVariable String id,
            @RequestPart("data") String data,
            @RequestPart(
                    value = "profileImage",
                    required = false
            )
            MultipartFile profileImage
    ) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        UserPayload userPayload = objectMapper.readValue(
                data,
                UserPayload.class
        );

        userService.updateAdmin(id,userPayload, profileImage);

        return ResponseEntity.ok("Admin Updated Successfully");
    }
}