package com.example.Ems_Pro.Repository;

import com.example.Ems_Pro.Entity.CompanyEntity;
import com.example.Ems_Pro.Entity.LocationEntity;
import com.example.Ems_Pro.Entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepositry extends JpaRepository<CompanyEntity, String> {
    Optional<CompanyEntity> findByCompanyEmail(String username);
}
