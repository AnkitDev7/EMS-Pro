package com.example.Ems_Pro.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ImageService {

    void deleteProfileImage(String imagePath);

    String uploadProfileImage(
            MultipartFile profileImage,
            String userId
    );
}
