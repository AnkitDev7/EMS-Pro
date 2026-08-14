package com.example.Ems_Pro.ServiceIMPL;

import com.example.Ems_Pro.Entity.CompanyEntity;
import com.example.Ems_Pro.Entity.LocationEntity;
import com.example.Ems_Pro.Entity.RoleEntity;
import com.example.Ems_Pro.Entity.UsersEntity;
import com.example.Ems_Pro.Payload.Request.UserPayload;
import com.example.Ems_Pro.Repository.CompanyRepositry;
import com.example.Ems_Pro.Repository.LocationRepositry;
import com.example.Ems_Pro.Repository.RoleRepositry;
import com.example.Ems_Pro.Repository.UserRepositry;
import com.example.Ems_Pro.Service.ImageService;
import com.example.Ems_Pro.Service.UserService;
import jakarta.transaction.Transactional;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class UserServiceIMP implements UserService {

    @Autowired
    private UserRepositry userRepositry;

    @Autowired
    private RoleRepositry roleRepositry;

    @Autowired
    private CompanyRepositry companyRepositry;

    @Autowired
    private LocationRepositry locationRepositry;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ImageService imageService;

    @Override
    @Transactional(rollbackOn =  Exception.class)
    public UsersEntity createAdmin(UserPayload userPayload, MultipartFile profileImage) {

        if (userRepositry.existsByEmail(userPayload.getEmail())) {

            throw new RuntimeException(
                    "User already exists with this email"
            );
        }

//        RoleEntity adminRole =
//                roleRepositry.findByRoleName("ADMIN")
//                        .orElseThrow(() ->
//                                new RuntimeException("ADMIN role not found")
//                        );




        // 3. Check location

        RoleEntity role =
                roleRepositry.findById(userPayload.getRoleId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Role not found: " +
                                                userPayload.getRoleId()
                                )
                        );


        CompanyEntity company =
                companyRepositry.findById(
                        userPayload.getCompanyId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Company not found"
                        )
                );

        LocationEntity location =
                locationRepositry.findById(
                                userPayload.getLocationId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Location not found"
                                )
                        );


        // 5. Create User
        UsersEntity user = new UsersEntity();

        user.setUserId(userPayload.getUserId());
        user.setName(userPayload.getName());
        user.setEmail(userPayload.getEmail());

        user.setPassword(
                passwordEncoder.encode(
                        userPayload.getPassword()
                )
        );

        user.setPhone_Number(
                userPayload.getPhoneNumber()
        );

        // Role dynamic
        user.setRole(role);

        // Company
        user.setCompany(company);

        // Location
        user.setLocation(location);

        // Department initially null
        user.setDepartment(null);

        // Status
        user.setStatus(
                UsersEntity.Status.ACTIVE
        );

/*         10. Admin ke liye ye NULL
        admin.setDepartment(null);

        admin.setManager(null);

        admin.setTeamLeader(null);
*/

        // Status
        user.setStatus(
                UsersEntity.Status.ACTIVE
        );


        // 12. Profile image
        if (profileImage != null &&
                !profileImage.isEmpty()) {

            String imagePath =
                    imageService.uploadProfileImage(
                            profileImage,
                            user.getUserId()
                    );

            user.setProfileImage(imagePath);
        }


        // 13. Save
        return userRepositry.save(user);

    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void updateAdmin(
            String id,
            UserPayload userPayload,
            MultipartFile profileImage
    ) {

        // 1. Find existing admin
        UsersEntity admin = userRepositry.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Admin not found with id: " + id)
                );


        // 2. Update name
        if (userPayload.getName() != null &&
                !userPayload.getName().isBlank()) {

            admin.setName(userPayload.getName());
        }


        // 3. Update phone number
        if (userPayload.getPhoneNumber() != null &&
                !userPayload.getPhoneNumber().isBlank()) {

            admin.setPhone_Number(userPayload.getPhoneNumber());
        }


        // 4. Update password
        if (userPayload.getPassword() != null &&
                !userPayload.getPassword().isBlank()) {

            admin.setPassword(
                    passwordEncoder.encode(
                            userPayload.getPassword()
                    )
            );
        }

        // 5. Update company
        if (userPayload.getCompanyId() != null) {

            CompanyEntity company =
                    companyRepositry.findById(
                                    userPayload.getCompanyId()
                            )
                            .orElseThrow(() ->
                                    new RuntimeException("Company not found")
                            );

            admin.setCompany(company);
        }


        // 6. Update location
        if (userPayload.getLocationId() != null) {

            LocationEntity location =
                    locationRepositry.findById(
                                    userPayload.getLocationId()
                            )
                            .orElseThrow(() ->
                                    new RuntimeException("Location not found")
                            );

            // Check if another admin already exists
            boolean adminExists =
                    userRepositry
                            .existsByLocation_LocationIdAndRole_RoleId(
                                    location.getLocationId(),
                                    admin.getRole().getRoleId()
                            );

            if (adminExists &&
                    !admin.getLocation().getLocationId()
                            .equals(location.getLocationId())) {

                throw new RuntimeException(
                        "Admin already exists for this location"
                );
            }

            admin.setLocation(location);
        }


        // 7. Update profile image
        if (profileImage != null &&
                !profileImage.isEmpty()) {

            String imagePath =
                    imageService.uploadProfileImage(
                            profileImage,
                            admin.getUserId()
                    );

            admin.setProfileImage(imagePath);
        }


        // 8. Save updated admin
        userRepositry.save(admin);
    }


}
