package com.example.Ems_Pro.ServiceIMPL;
import com.example.Ems_Pro.Entity.*;
import com.example.Ems_Pro.Payload.Request.UserPayload;
import com.example.Ems_Pro.Payload.Response.*;
import com.example.Ems_Pro.Repository.*;
import com.example.Ems_Pro.Service.ImageService;
import com.example.Ems_Pro.Service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.internal.util.Lists;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
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

    @Autowired
    private DepartmentRepositry departmentRepositry;


    private UserResponse mapToResponse(UsersEntity user) {

        UserResponse userResponse = new UserResponse();

        userResponse.setUserId(user.getUserId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhoneNo(user.getPhoneNo());
        userResponse.setStatus(user.getStatus());
        userResponse.setUserCreatedAt(user.getUserCreatedAt());
        userResponse.setProfileImage(user.getProfileImage());

        // Company
        if (user.getCompany() != null) {

            CompanyResponse companyResponse = new CompanyResponse();

            companyResponse.setCompanyId(
                    user.getCompany().getCompanyId()
            );

            companyResponse.setCompanyName(
                    user.getCompany().getCompanyName()
            );

            companyResponse.setCompanyAddress(
                    user.getCompany().getCompanyAddress()
            );

            companyResponse.setCompanyEmail(
                    user.getCompany().getCompanyEmail()
            );

            companyResponse.setCompanyPhoneNo(
                    user.getCompany().getCompanyPhoneNo()
            );

            userResponse.setCompany(companyResponse);
        }

        // Department
        if (user.getDepartment() != null) {

            DepartmentResponse departmentResponse =
                    new DepartmentResponse();

            departmentResponse.setDepartmentId(
                    user.getDepartment().getDepartmentId()
            );

            departmentResponse.setDepartmentName(
                    user.getDepartment().getDepartmentName()
            );

            departmentResponse.setStatus(
                    user.getDepartment().getStatus()
            );

            userResponse.setDepartment(departmentResponse);
        }

        // Role
        if (user.getRole() != null) {

            RoleResponse roleResponse = new RoleResponse();

            roleResponse.setRoleId(
                    user.getRole().getRoleId()
            );

            roleResponse.setRoleName(
                    user.getRole().getRoleName()
            );

            userResponse.setRole(roleResponse);
        }

        if (user.getLocation() != null) {

            LocationResponse locationResponse = new LocationResponse();

            locationResponse.setLocationId(
                    user.getLocation().getLocationId()
            );

            locationResponse.setAddress(
                    user.getLocation().getAddress()
            );

            locationResponse.setCity(
                    user.getLocation().getCity()
            );

            locationResponse.setState(
                    user.getLocation().getState()
            );


            userResponse.setLocation(locationResponse);

            CompanyResponse companyResponse = new CompanyResponse();

            companyResponse.setCompanyId(
                    user.getCompany().getCompanyId()
            );

            companyResponse.setCompanyName(
                    user.getCompany().getCompanyName()
            );

            companyResponse.setCompanyAddress(
                    user.getCompany().getCompanyAddress()
            );

            companyResponse.setCompanyEmail(
                    user.getCompany().getCompanyEmail()
            );

            companyResponse.setCompanyPhoneNo(
                    user.getCompany().getCompanyPhoneNo()
            );

            userResponse.setCompany(companyResponse);
        }

        return userResponse;
    }

    private UsersEntity getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepositry
                .findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("Current user not found")
                );
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public UsersEntity createUser(
            UserPayload userPayload,
            MultipartFile profileImage) {

        // 1. Check duplicate email
        if (userRepositry.existsByEmail(userPayload.getEmail())) {

            throw new RuntimeException(
                    "User already exists with this email"
            );
        }

        // 2. Find Role
        RoleEntity role =
                roleRepositry.findById(
                        userPayload.getRoleId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Role not found: "
                                        + userPayload.getRoleId()
                        )
                );

        // 3. Find Company
        CompanyEntity company =
                companyRepositry.findById(
                        userPayload.getCompanyId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Company not found: "
                                        + userPayload.getCompanyId()
                        )
                );

        // 4. Find Location
        LocationEntity location =
                locationRepositry.findById(
                        userPayload.getLocationId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Location not found: "
                                        + userPayload.getLocationId()
                        )
                );

//         * One Location = One Admin

        DepartmentEntity department = null;

        if ("ROLE002".equals(role.getRoleId())) {

            // Check Admin already exists in this location

            boolean adminExists =
                    userRepositry
                            .existsByLocation_LocationIdAndRole_RoleId(
                                    location.getLocationId(),
                                    role.getRoleId()
                            );

            if (adminExists) {

                throw new RuntimeException(
                        "Admin already exists for this location"
                );
            }

            // Admin department will remain NULL
            department = null;
        }

        /*
         * ==========================================================
         * MANAGER
         * ==========================================================
         *
         * Rule:
         *
         * One Location + One Department = One Manager
         */

        else if ("ROLE004".equals(role.getRoleId())) {

            // Department is mandatory for Manager

            if (userPayload.getDepartmentId() == null ||
                    userPayload.getDepartmentId().isBlank()) {

                throw new RuntimeException(
                        "Department is required for Manager"
                );
            }

            // Find Department

            department =
                    departmentRepositry.findById(
                            userPayload.getDepartmentId()
                    ).orElseThrow(() ->
                            new RuntimeException(
                                    "Department not found: "
                                            + userPayload.getDepartmentId()
                            )
                    );

            // Check Manager already exists
            // for this Location + Department

            boolean managerExists =
                    userRepositry
                            .existsByLocation_LocationIdAndDepartment_DepartmentIdAndRole_RoleId(
                                    location.getLocationId(),
                                    department.getDepartmentId(),
                                    role.getRoleId()
                            );

            if (managerExists) {

                throw new RuntimeException(
                        "Manager already exists for this department and location"
                );
            }
        }

        /*
         * ==========================================================
         * OTHER USERS
         * ==========================================================
         *
         * Employee etc.
         *
         * Department is required.
         */

        else {

            if (userPayload.getDepartmentId() != null &&
                    !userPayload.getDepartmentId().isBlank()) {

                department =
                        departmentRepositry.findById(
                                userPayload.getDepartmentId()
                        ).orElseThrow(() ->
                                new RuntimeException(
                                        "Department not found: "
                                                + userPayload.getDepartmentId()
                                )
                        );
            }
        }

        // 5. Create User

        UsersEntity user = new UsersEntity();

        user.setUserId(
                userPayload.getUserId()
        );

        user.setName(
                userPayload.getName()
        );

        user.setEmail(
                userPayload.getEmail()
        );

        // Encode password

        user.setPassword(
                passwordEncoder.encode(
                        userPayload.getPassword()
                )
        );

        user.setPhoneNo(
                userPayload.getPhoneNumber()
        );

        // Role

        user.setRole(role);

        // Company

        user.setCompany(company);

        // Location

        user.setLocation(location);

        user.setDepartment(department);

        // Status

        user.setStatus(
                userPayload.getStatus()
        );

        user.setUserCreatedAt(
                LocalDateTime.now()
        );

        // 6. Profile Image

        if (profileImage != null &&
                !profileImage.isEmpty()) {

            String imagePath =
                    imageService.uploadProfileImage(
                            profileImage,
                            user.getUserId()
                    );

            user.setProfileImage(
                    imagePath
            );
        }

        // 7. Save User

        return userRepositry.save(user);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void updateUser(
            String id,
            UserPayload userPayload,
            MultipartFile profileImage
    ) {

        // 1. Find User
        UsersEntity user =
                userRepositry.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found with id: " + id
                                )
                        );

        // 2. Update Name

        if (userPayload.getName() != null &&
                !userPayload.getName().isBlank()) {

            user.setName(
                    userPayload.getName()
            );
        }


        // 3. Update Phone

        if (userPayload.getPhoneNumber() != null &&
                !userPayload.getPhoneNumber().isBlank()) {

            // Check duplicate phone number

            if (!user.getPhoneNo().equals(
                    userPayload.getPhoneNumber())) {

                boolean phoneExists =
                        userRepositry.existsByPhoneNo(
                                userPayload.getPhoneNumber()
                        );

                if (phoneExists) {

                    throw new RuntimeException(
                            "User already exists with this phone number"
                    );
                }
            }

            user.setPhoneNo(
                    userPayload.getPhoneNumber()
            );
        }



        // 4. Update Password
        if (userPayload.getPassword() != null &&
                !userPayload.getPassword().isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(
                            userPayload.getPassword()
                    )
            );
        }

        // 5. Update Company

        CompanyEntity company = user.getCompany();

        if (userPayload.getCompanyId() != null &&
                !userPayload.getCompanyId().isBlank()) {

            company =
                    companyRepositry.findById(
                                    userPayload.getCompanyId()
                            )
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Company not found: "
                                                    + userPayload.getCompanyId()
                                    )
                            );

            user.setCompany(company);
        }


        // 6. Update Location

        LocationEntity location = user.getLocation();

        if (userPayload.getLocationId() != null) {

            location =
                    locationRepositry.findById(
                                    userPayload.getLocationId()
                            )
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Location not found: "
                                                    + userPayload.getLocationId()
                                    )
                            );

            user.setLocation(location);
        }


        // 7. Role

        RoleEntity role = user.getRole();

        if (userPayload.getRoleId() != null &&
                !userPayload.getRoleId().isBlank()) {

            role =
                    roleRepositry.findById(
                                    userPayload.getRoleId()
                            )
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Role not found: "
                                                    + userPayload.getRoleId()
                                    )
                            );

            user.setRole(role);
        }

        // 8. Department Logic

        DepartmentEntity department = null;


        // ADMIN

        if ("ROLE002".equals(role.getRoleId())) {

            department = null;

            boolean adminExists =
                    userRepositry
                            .existsByLocation_LocationIdAndRole_RoleId(
                                    location.getLocationId(),
                                    role.getRoleId()
                            );

            if (adminExists &&
                    !(
                            user.getLocation() != null &&
                                    user.getLocation()
                                            .getLocationId()
                                            .equals(location.getLocationId())
                                    &&
                                    user.getRole() != null &&
                                    user.getRole()
                                            .getRoleId()
                                            .equals(role.getRoleId())
                    )) {

                throw new RuntimeException(
                        "Admin already exists for this location"
                );
            }
        }



        // MANAGER

        else if ("ROLE004".equals(role.getRoleId())) {


            if (userPayload.getDepartmentId() == null ||
                    userPayload.getDepartmentId().isBlank()) {

                throw new RuntimeException(
                        "Department is required for Manager"
                );
            }

            department =
                    departmentRepositry.findById(
                                    userPayload.getDepartmentId()
                            )
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Department not found: "
                                                    + userPayload.getDepartmentId()
                                    )
                            );


            boolean managerExists =
                    userRepositry
                            .existsByLocation_LocationIdAndDepartment_DepartmentIdAndRole_RoleId(
                                    location.getLocationId(),
                                    department.getDepartmentId(),
                                    role.getRoleId()
                            );



            if (managerExists &&
                    !(
                            user.getLocation() != null &&
                                    user.getLocation()
                                            .getLocationId()
                                            .equals(location.getLocationId())
                                    &&
                                    user.getDepartment() != null &&
                                    user.getDepartment()
                                            .getDepartmentId()
                                            .equals(department.getDepartmentId())
                                    &&
                                    user.getRole() != null &&
                                    user.getRole()
                                            .getRoleId()
                                            .equals(role.getRoleId())
                    )) {

                throw new RuntimeException(
                        "Manager already exists for this department and location"
                );
            }
        }


        // EMPLOYEE / OTHER USER

        else {


            if (userPayload.getDepartmentId() == null ||
                    userPayload.getDepartmentId().isBlank()) {

                throw new RuntimeException(
                        "Department is required for this user"
                );
            }


            department =
                    departmentRepositry.findById(
                                    userPayload.getDepartmentId()
                            )
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Department not found: "
                                                    + userPayload.getDepartmentId()
                                    )
                            );
        }

        // 9. Set Department

        user.setDepartment(
                department
        );



        // 10. Update Status

        if (userPayload.getStatus() != null &&
                !userPayload.getStatus().isBlank()) {

            user.setStatus(
                    userPayload.getStatus()
            );
        }


        // 11. Profile Image

        if (profileImage != null &&
                !profileImage.isEmpty()) {

            String imagePath =
                    imageService.uploadProfileImage(
                            profileImage,
                            user.getUserId()
                    );

            user.setProfileImage(
                    imagePath
            );
        }

        // 12. Save
        userRepositry.save(user);
    }

    @Override
    public UsersEntity deleteSingleUser(String id) {
        Optional<UsersEntity> byId = userRepositry.findById(id);

        if (byId.isPresent()) {
            UsersEntity usersEntity = byId.get();
            userRepositry.delete(usersEntity);
            return usersEntity;
        }

        throw new RuntimeException("User not found: " + id);
    }

    @Override
    public UserResponse getSingleUser(String id) {

        UsersEntity currentUser = getCurrentUser();

        UsersEntity targetUser = userRepositry.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found: " + id
                        )
                );

        String currentRoleId = currentUser.getRole().getRoleId();

        if ("ROLE001".equals(currentRoleId)) {
            return mapToResponse(currentUser);
        }

        if ("ROLE002".equals(currentRoleId)) {

            boolean sameLocation =
                    currentUser.getLocation() != null &&
                            targetUser.getLocation() != null &&
                            currentUser.getLocation()
                                    .getLocationId()
                                    .equals(
                                            targetUser.getLocation()
                                                    .getLocationId()
                                    );

            if (!sameLocation) {
                throw new RuntimeException(
                        "You cannot access user from another location"
                );
            }
        }

        return mapToResponse(targetUser);
    }

    @Override
    public List<UserResponse> getAllUsers() {

        UsersEntity currentUser = getCurrentUser();

        String roleId = currentUser.getRole().getRoleId();

        List<UsersEntity> users ;

        if ("ROLE001".equals(roleId)) {
            users = userRepositry.findAll();
        }

        else if ("ROLE002".equals(roleId)) {

            users =
                    userRepositry.findByLocation_LocationId(
                            currentUser.getLocation()
                                    .getLocationId()
                    );

        } else if ("ROLE004".equals(roleId)) {

            users = userRepositry.findByLocation_LocationIdAndDepartment_DepartmentIdAndRole_RoleId(
                    currentUser.getLocation()
                            .getLocationId(),
                    currentUser.getDepartment()
                            .getDepartmentId(),
                    "ROLE003"
            );
        } else if ("ROLE003".equals(roleId)) {

            users = List.of(currentUser);
            
        }else {
            throw new RuntimeException(
                    "Access denied"
            );
        }

        return users
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public Page<UserResponse> findAllUsers(Pageable pageable) {

        UsersEntity currentUser = getCurrentUser();

        String roleId = currentUser.getRole().getRoleId();

        if ("ROLE001".equals(roleId)) {
            Page<UsersEntity> allUsers = userRepositry.findAll(pageable);
            return allUsers.map(this::mapToResponse);
        }

        if ("ROLE002".equals(roleId)) {

            Page<UsersEntity> users = userRepositry.findByLocation_LocationId(
                    currentUser.getLocation()
                            .getLocationId(),
                    pageable
            );

            return users.map(this::mapToResponse);
        }

        if ("ROLE004".equals(roleId)) {

            Page<UsersEntity> users =
                    userRepositry
                            .findByLocation_LocationIdAndDepartment_DepartmentIdAndRole_RoleId(
                                    currentUser
                                            .getLocation()
                                            .getLocationId(),

                                    currentUser
                                            .getDepartment()
                                            .getDepartmentId(),

                                    "ROLE003",

                                    pageable
                            );

            return users.map(this::mapToResponse);
        }

        if ("ROLE003".equals(roleId)) {

            Page<UsersEntity> users =
                    userRepositry.findByUserId(
                            currentUser.getUserId(),
                            pageable
                    );

            return users.map(this::mapToResponse);
        }


        throw new RuntimeException(
                "Access denied"
        );


    }


    @Override
    public Page<UserResponse> findAllUsersByRole(
            String roleId,
            Pageable pageable) {

        UsersEntity currentUser = getCurrentUser();

        String currentRoleId =
                currentUser.getRole().getRoleId();

        Page<UsersEntity> users;


        // SUPER ADMIN
        if ("ROLE001".equals(currentRoleId)) {

            users =
                    userRepositry.findByRole_RoleId(
                            roleId,
                            pageable
                    );
        }


        // ADMIN
        else if ("ROLE002".equals(currentRoleId)) {

            users =
                    userRepositry
                            .findByLocation_LocationIdAndRole_RoleId(
                                    currentUser.getLocation().getLocationId(),
                                    roleId,
                                    pageable
                            );
        }


        // MANAGER
        else if ("ROLE004".equals(currentRoleId)) {

            // Manager can only see employees
            if (!"ROLE003".equals(roleId)) {

                throw new RuntimeException(
                        "Manager can only access employees"
                );
            }

            users =
                    userRepositry
                            .findByLocation_LocationIdAndDepartment_DepartmentIdAndRole_RoleId(
                                    currentUser.getLocation().getLocationId(),
                                    currentUser.getDepartment().getDepartmentId(),
                                    "ROLE003",
                                    pageable
                            );
        }


        // EMPLOYEE
        else if ("ROLE003".equals(currentRoleId)) {

            // Employee sirf apne aap ko
            if (!currentUser.getRole().getRoleId().equals(roleId)) {

                throw new RuntimeException(
                        "Employee cannot access other roles"
                );
            }

            users =
                    userRepositry.findByUserId(
                            currentUser.getUserId(),
                            pageable
                    );
        }


        else {

            throw new RuntimeException(
                    "Access denied"
            );
        }


        return users.map(this::mapToResponse);
    }


}
