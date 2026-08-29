package com.example.Ems_Pro.ServiceIMPL;

import com.example.Ems_Pro.Entity.*;
import com.example.Ems_Pro.Payload.Request.UserPayload;
import com.example.Ems_Pro.Payload.Response.*;
import com.example.Ems_Pro.Repository.*;
import com.example.Ems_Pro.Service.ImageService;
import com.example.Ems_Pro.Service.UserService;
import jakarta.transaction.Transactional;
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

        UsersEntity currentUser = getCurrentUser();

        String currentRoleId =
                currentUser.getRole().getRoleId();

        String requestedRoleId =
                userPayload.getRoleId();


// SUPER ADMIN

        if ("ROLE001".equals(currentRoleId)) {

            // SUPER_ADMIN can create users
        }


// ADMIN

        else if ("ROLE002".equals(currentRoleId)) {

            if (!currentUser.getCompany()
                    .getCompanyId()
                    .equals(userPayload.getCompanyId())) {

                throw new RuntimeException(
                        "You can only create users in your own company"
                );
            }

            if (!"ROLE004".equals(requestedRoleId) &&
                    !"ROLE003".equals(requestedRoleId)) {

                throw new RuntimeException(
                        "Admin can only create Manager or Employee"
                );
            }

            if (userPayload.getLocationId() == null) {

                throw new RuntimeException(
                        "Location is required"
                );
            }

            if (!currentUser.getLocation()
                    .getLocationId()
                    .equals(userPayload.getLocationId())) {

                throw new RuntimeException(
                        "Admin can only create users in his own location"
                );
            }
        }


// MANAGER
        else if ("ROLE004".equals(currentRoleId)) {

            if (!currentUser.getCompany()
                    .getCompanyId()
                    .equals(userPayload.getCompanyId())) {

                throw new RuntimeException(
                        "You can only create users in your own company"
                );
            }

            if (!"ROLE003".equals(requestedRoleId)) {

                throw new RuntimeException(
                        "Manager can only create Employee"
                );
            }

            if (userPayload.getLocationId() == null) {

                throw new RuntimeException(
                        "Location is required"
                );
            }

            if (userPayload.getDepartmentId() == null ||
                    userPayload.getDepartmentId().isBlank()) {

                throw new RuntimeException(
                        "Department is required"
                );
            }

            if (!currentUser.getLocation()
                    .getLocationId()
                    .equals(userPayload.getLocationId())) {

                throw new RuntimeException(
                        "Manager can only create employee in his own location"
                );
            }

            if (!currentUser.getDepartment()
                    .getDepartmentId()
                    .equals(userPayload.getDepartmentId())) {

                throw new RuntimeException(
                        "Manager can only create employee in his own department"
                );
            }
        }

// EMPLOYEE

        else if ("ROLE003".equals(currentRoleId)) {

            throw new RuntimeException(
                    "Employee is not allowed to create users"
            );
        }


// UNKNOWN ROLE

        else {

            throw new RuntimeException(
                    "Access denied"
            );
        }


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
        } else if ("ROLE004".equals(role.getRoleId())) {

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
        } else {

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

        UsersEntity currentUser = getCurrentUser();


        UsersEntity user =
                userRepositry.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found with id: " + id
                                )
                        );

        String currentRoleId =
                currentUser.getRole().getRoleId();

        String targetRoleId =
                user.getRole().getRoleId();


        // AUTHORIZATION

        // SUPER ADMIN
        if ("ROLE001".equals(currentRoleId)) {

            // Super Admin can update any user

        }


        // ADMIN
        else if ("ROLE002".equals(currentRoleId)) {

            // Admin cannot update another Admin
            if ("ROLE002".equals(targetRoleId)) {

                throw new RuntimeException(
                        "Admin cannot update another Admin"
                );
            }

            // Same company
            if (currentUser.getCompany() == null ||
                    user.getCompany() == null ||
                    !currentUser.getCompany()
                            .getCompanyId()
                            .equals(
                                    user.getCompany()
                                            .getCompanyId()
                            )) {

                throw new RuntimeException(
                        "Admin can only update users from his own company"
                );
            }

            // Same location
            if (currentUser.getLocation() == null ||
                    user.getLocation() == null ||
                    !currentUser.getLocation()
                            .getLocationId()
                            .equals(
                                    user.getLocation()
                                            .getLocationId()
                            )) {

                throw new RuntimeException(
                        "Admin can only update users from his own location"
                );
            }

            if ("ROLE001".equals(userPayload.getRoleId())) {

                throw new RuntimeException(
                        "Admin cannot assign Super Admin role"
                );
            }
        }


        // MANAGER
        else if ("ROLE004".equals(currentRoleId)) {

            // Manager can update Employee only
            if (!"ROLE003".equals(targetRoleId)) {

                throw new RuntimeException(
                        "Manager can only update employees"
                );
            }

            // Same company
            if (currentUser.getCompany() == null ||
                    user.getCompany() == null ||
                    !currentUser.getCompany()
                            .getCompanyId()
                            .equals(
                                    user.getCompany()
                                            .getCompanyId()
                            )) {

                throw new RuntimeException(
                        "Manager can only update users from his own company"
                );
            }

            // Same location
            if (currentUser.getLocation() == null ||
                    user.getLocation() == null ||
                    !currentUser.getLocation()
                            .getLocationId()
                            .equals(
                                    user.getLocation()
                                            .getLocationId()
                            )) {

                throw new RuntimeException(
                        "Manager can only update employees from his own location"
                );
            }

            // Same department
            if (currentUser.getDepartment() == null ||
                    user.getDepartment() == null ||
                    !currentUser.getDepartment()
                            .getDepartmentId()
                            .equals(
                                    user.getDepartment()
                                            .getDepartmentId()
                            )) {

                throw new RuntimeException(
                        "Manager can only update employees from his own department"
                );
            }

            // Manager cannot change Employee role
            if (userPayload.getRoleId() != null &&
                    !userPayload.getRoleId().isBlank() &&
                    !"ROLE003".equals(userPayload.getRoleId())) {

                throw new RuntimeException(
                        "Manager cannot change employee role"
                );
            }

            // Manager cannot change company
            if (userPayload.getCompanyId() != null &&
                    !userPayload.getCompanyId().isBlank() &&
                    !currentUser.getCompany()
                            .getCompanyId()
                            .equals(userPayload.getCompanyId())) {

                throw new RuntimeException(
                        "Manager cannot change employee company"
                );
            }

            // Manager cannot change location
            if (userPayload.getLocationId() != null &&
                    !currentUser.getLocation()
                            .getLocationId()
                            .equals(userPayload.getLocationId())) {

                throw new RuntimeException(
                        "Manager cannot change employee location"
                );
            }

            // Manager cannot change department
            if (userPayload.getDepartmentId() != null &&
                    !currentUser.getDepartment()
                            .getDepartmentId()
                            .equals(userPayload.getDepartmentId())) {

                throw new RuntimeException(
                        "Manager cannot change employee department"
                );
            }
        }


        // EMPLOYEE
        else if ("ROLE003".equals(currentRoleId)) {

            // Employee can update only own profile
            if (!currentUser.getUserId()
                    .equals(user.getUserId())) {

                throw new RuntimeException(
                        "Employee can only update their own profile"
                );
            }

            // Employee cannot change role
            if (userPayload.getRoleId() != null &&
                    !userPayload.getRoleId().isBlank()) {

                throw new RuntimeException(
                        "Employee cannot change role"
                );
            }

            // Employee cannot change company
            if (userPayload.getCompanyId() != null &&
                    !userPayload.getCompanyId().isBlank()) {

                throw new RuntimeException(
                        "Employee cannot change company"
                );
            }

            // Employee cannot change location
            if (userPayload.getLocationId() != null) {

                throw new RuntimeException(
                        "Employee cannot change location"
                );
            }

            // Employee cannot change department
            if (userPayload.getDepartmentId() != null &&
                    !userPayload.getDepartmentId().isBlank()) {

                throw new RuntimeException(
                        "Employee cannot change department"
                );
            }
        }


        else {

            throw new RuntimeException(
                    "Access denied"
            );
        }


        // Name
        if (userPayload.getName() != null &&
                !userPayload.getName().isBlank()) {

            user.setName(
                    userPayload.getName()
            );
        }


        // Phone
        if (userPayload.getPhoneNumber() != null &&
                !userPayload.getPhoneNumber().isBlank()) {

            if (user.getPhoneNo() == null ||
                    !user.getPhoneNo()
                            .equals(userPayload.getPhoneNumber())) {

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


        // Password
        if (userPayload.getPassword() != null &&
                !userPayload.getPassword().isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(
                            userPayload.getPassword()
                    )
            );
        }

        // COMPANY
        CompanyEntity company =
                user.getCompany();

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


        // LOCATION
        LocationEntity location =
                user.getLocation();

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


        // ROLE
        RoleEntity role =
                user.getRole();

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


        // DEPARTMENT

        DepartmentEntity department =
                user.getDepartment();


        // ADMIN
        if ("ROLE002".equals(role.getRoleId())) {

            department = null;

            boolean adminExists =
                    userRepositry
                            .existsByLocation_LocationIdAndRole_RoleId(
                                    location.getLocationId(),
                                    role.getRoleId()
                            );

            boolean currentUserIsSameAdmin =
                    user.getLocation() != null &&
                            user.getLocation()
                                    .getLocationId()
                                    .equals(
                                            location.getLocationId()
                                    ) &&
                            user.getRole() != null &&
                            user.getRole()
                                    .getRoleId()
                                    .equals(
                                            role.getRoleId()
                                    );

            if (adminExists && !currentUserIsSameAdmin) {

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

            boolean currentUserIsSameManager =
                    user.getLocation() != null &&
                            user.getLocation()
                                    .getLocationId()
                                    .equals(
                                            location.getLocationId()
                                    ) &&
                            user.getDepartment() != null &&
                            user.getDepartment()
                                    .getDepartmentId()
                                    .equals(
                                            department.getDepartmentId()
                                    ) &&
                            user.getRole() != null &&
                            user.getRole()
                                    .getRoleId()
                                    .equals(
                                            role.getRoleId()
                                    );

            if (managerExists && !currentUserIsSameManager) {

                throw new RuntimeException(
                        "Manager already exists for this department and location"
                );
            }
        }


        // EMPLOYEE / OTHER
        else {

            if (userPayload.getDepartmentId() != null &&
                    !userPayload.getDepartmentId().isBlank()) {

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
        }


        user.setDepartment(department);


        // STATUS

        if (userPayload.getStatus() != null &&
                !userPayload.getStatus().isBlank()) {

            user.setStatus(
                    userPayload.getStatus()
            );
        }


        // PROFILE IMAGE

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


        // SAVE

        userRepositry.save(user);
    }



    @Override
    @Transactional
    public UsersEntity deleteSingleUser(String id) {


        UsersEntity currentUser = getCurrentUser();

        UsersEntity targetUser =
                userRepositry.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found: " + id
                                )
                        );


        String currentRoleId =
                currentUser.getRole().getRoleId();



        // SUPER ADMIN
        if ("ROLE001".equals(currentRoleId)) {

            userRepositry.delete(targetUser);

            return targetUser;
        }

        // ADMIN
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
                        "Admin can only delete users from the same location"
                );
            }

            userRepositry.delete(targetUser);

            return targetUser;
        }


        // MANAGER
        if ("ROLE004".equals(currentRoleId)) {

            boolean sameLocation =
                    currentUser.getLocation() != null &&
                            targetUser.getLocation() != null &&
                            currentUser.getLocation()
                                    .getLocationId()
                                    .equals(
                                            targetUser.getLocation()
                                                    .getLocationId()
                                    );


            boolean sameDepartment =
                    currentUser.getDepartment() != null &&
                            targetUser.getDepartment() != null &&
                            currentUser.getDepartment()
                                    .getDepartmentId()
                                    .equals(
                                            targetUser.getDepartment()
                                                    .getDepartmentId()
                                    );


            boolean targetIsEmployee =
                    targetUser.getRole() != null &&
                            "ROLE003".equals(
                                    targetUser.getRole().getRoleId()
                            );


            if (!sameLocation ||
                    !sameDepartment ||
                    !targetIsEmployee) {

                throw new RuntimeException(
                        "Manager can only delete employees from the same location and department"
                );
            }

            userRepositry.delete(targetUser);

            return targetUser;
        }


        // EMPLOYEE
        if ("ROLE003".equals(currentRoleId)) {

            throw new RuntimeException(
                    "Employee is not allowed to delete users"
            );
        }

        // UNKNOWN ROLE
        throw new RuntimeException(
                "Access denied"
        );
    }



    @Override
    public UserResponse getSingleUser(String id) {

        UsersEntity currentUser = getCurrentUser();

        UsersEntity targetUser =
                userRepositry.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found: " + id
                                )
                        );

        String currentRoleId =
                currentUser.getRole().getRoleId();

        if ("ROLE001".equals(currentRoleId)) {

            return mapToResponse(targetUser);
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

            return mapToResponse(targetUser);
        }

        if ("ROLE004".equals(currentRoleId)) {

            boolean sameLocation =
                    currentUser.getLocation() != null &&
                            targetUser.getLocation() != null &&
                            currentUser.getLocation()
                                    .getLocationId()
                                    .equals(
                                            targetUser.getLocation()
                                                    .getLocationId()
                                    );


            boolean sameDepartment =
                    currentUser.getDepartment() != null &&
                            targetUser.getDepartment() != null &&
                            currentUser.getDepartment()
                                    .getDepartmentId()
                                    .equals(
                                            targetUser.getDepartment()
                                                    .getDepartmentId()
                                    );


            boolean targetIsEmployee =
                    targetUser.getRole() != null &&
                            "ROLE003".equals(
                                    targetUser.getRole().getRoleId()
                            );


            if (!sameLocation ||
                    !sameDepartment ||
                    !targetIsEmployee) {

                throw new RuntimeException(
                        "Manager can only access employees from the same location and department"
                );
            }

            return mapToResponse(targetUser);
        }

        if ("ROLE003".equals(currentRoleId)) {

            boolean ownProfile =
                    currentUser.getUserId()
                            .equals(targetUser.getUserId());

            if (!ownProfile) {

                throw new RuntimeException(
                        "Employee can only access their own profile"
                );
            }

            return mapToResponse(targetUser);
        }


        throw new RuntimeException(
                "Access denied"
        );
    }



    @Override
    public List<UserResponse> getAllUsers() {

        UsersEntity currentUser = getCurrentUser();

        String roleId = currentUser.getRole().getRoleId();

        List<UsersEntity> users;

        if ("ROLE001".equals(roleId)) {
            users = userRepositry.findAll();
        } else if ("ROLE002".equals(roleId)) {

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

        } else {
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
        } else {

            throw new RuntimeException(
                    "Access denied"
            );
        }


        return users.map(this::mapToResponse);
    }


}
