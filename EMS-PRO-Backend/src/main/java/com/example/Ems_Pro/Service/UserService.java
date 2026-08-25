package com.example.Ems_Pro.Service;

import com.example.Ems_Pro.Entity.UsersEntity;
import com.example.Ems_Pro.Payload.Request.UserPayload;
import com.example.Ems_Pro.Payload.Response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    UsersEntity deleteSingleUser(String id);

    UserResponse getSingleUser(String id);

    List<UserResponse> getAllUsers();

    Page<UserResponse> findAllUsers(Pageable pageable);

    Page<UserResponse> findAllUsersByRole(String roleId, Pageable pageable);
}
