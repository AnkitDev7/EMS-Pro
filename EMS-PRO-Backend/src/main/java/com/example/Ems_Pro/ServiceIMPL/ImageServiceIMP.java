package com.example.Ems_Pro.ServiceIMPL;

import com.example.Ems_Pro.Service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageServiceIMP implements ImageService {

    @Value("${app.upload.profile}")
    private String profileUploadPath;

    @Override
    public String uploadProfileImage(
            MultipartFile profileImage,
            String userId) {

        try {

            // 1. Folder create/check
            File directory =
                    new File(profileUploadPath);

            if (!directory.exists()) {
                directory.mkdirs();
            }


            // 2. Original filename
            String originalFileName =
                    profileImage.getOriginalFilename();


            // 3. Extension
            String extension = "";

            if (originalFileName != null &&
                    originalFileName.contains(".")) {

                extension =
                        originalFileName.substring(
                                originalFileName.lastIndexOf(".")
                        );
            }


            // 4. New filename
            String fileName =
                    userId + extension;


            // 5. Complete file path
            String filePath =
                    profileUploadPath
                            + File.separator
                            + fileName;


            // DEBUG
            System.out.println(
                    "UPLOAD PATH = " + profileUploadPath
            );

            System.out.println(
                    "FILE PATH = " + filePath
            );


            // 6. Save image
            profileImage.transferTo(
                    new File(filePath)
            );


            // 7. Database me save hone wala path
            return "img/profile/" + fileName;


        } catch (Exception e) {

            throw new RuntimeException(
                    "Profile image upload failed",
                    e
            );
        }
    }

    @Override
    public void deleteProfileImage(String imagePath) {

        if (imagePath == null || imagePath.isBlank()) {
            return;
        }

        try {
            String fileName =
                    Paths.get(imagePath).getFileName().toString();

            Path path = Paths.get(
                    profileUploadPath,
                    fileName
            );

            Files.deleteIfExists(path);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to delete profile image",
                    e
            );
        }
    }
}