package com.example.Ems_Pro.Repository;

import com.example.Ems_Pro.Entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepositry extends JpaRepository<RoleEntity,String> {

}
