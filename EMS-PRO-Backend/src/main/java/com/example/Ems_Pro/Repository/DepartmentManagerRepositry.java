package com.example.Ems_Pro.Repository;

import com.example.Ems_Pro.Entity.DepartmentLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentManagerRepositry extends JpaRepository<DepartmentLocationEntity,Integer> {
}
