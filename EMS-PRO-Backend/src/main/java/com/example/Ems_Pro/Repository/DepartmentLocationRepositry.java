package com.example.Ems_Pro.Repository;

import com.example.Ems_Pro.Entity.DepartmentLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentLocationRepositry extends JpaRepository<DepartmentLocationEntity,Integer> {
}
