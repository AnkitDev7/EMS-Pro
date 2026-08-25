package com.example.Ems_Pro.Repository;

import com.example.Ems_Pro.Entity.UsersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepositry extends JpaRepository<UsersEntity, String> {


    Optional<UsersEntity> findByEmail(String username);

    boolean existsByEmail(String email);

    boolean existsByLocation_LocationIdAndRole_RoleId(
            Integer locationId,
            String roleId
    );

    boolean existsByLocation_LocationIdAndDepartment_DepartmentIdAndRole_RoleId(
            Integer locationId,
            String departmentId,
            String roleId
    );

    boolean existsByPhoneNo(String phoneNo);

    Page<UsersEntity> findByRole_RoleId(
            String roleId,
            Pageable pageable
    );

    List<UsersEntity> findByLocation_LocationId(Integer locationId);

    List<UsersEntity> findByLocation_LocationIdAndDepartment_DepartmentIdAndRole_RoleId(
            Integer locationId,
            String departmentId,
            String roleId
    );

//    Optional<UsersEntity> findByUserId(String userId);

    Page<UsersEntity> findByLocation_LocationId(
            Integer locationId,
            Pageable pageable
    );

    Page<UsersEntity>
    findByLocation_LocationIdAndDepartment_DepartmentIdAndRole_RoleId(
            Integer locationId,
            String departmentId,
            String roleId,
            Pageable pageable
    );

    Page<UsersEntity> findByUserId(
            String userId,
            Pageable pageable
    );

    Page<UsersEntity> findByLocation_LocationIdAndRole_RoleId(
            Integer locationId,
            String roleId,
            Pageable pageable
    );
}