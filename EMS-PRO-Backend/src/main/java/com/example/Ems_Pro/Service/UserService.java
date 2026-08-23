package com.example.Ems_Pro.Service;

import com.example.Ems_Pro.Entity.UsersEntity;
import com.example.Ems_Pro.Payload.Request.UserPayload;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UserService {
    UsersEntity createUser(
            UserPayload userPayload,
            MultipartFile profileImage
    );

    void updateUser(
            String id,
            UserPayload userPayload,
            MultipartFile profileImage
    );
}
