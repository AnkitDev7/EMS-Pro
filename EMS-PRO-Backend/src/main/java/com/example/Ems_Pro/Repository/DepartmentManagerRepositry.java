package com.example.Ems_Pro.Repository;
import com.example.Ems_Pro.Entity.DepartmentEntity;
import com.example.Ems_Pro.Entity.DepartmentManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentManagerRepositry extends JpaRepository<DepartmentManager,DepartmentEntity> {

}
