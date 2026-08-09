package com.example.Ems_Pro.Repository;
import com.example.Ems_Pro.Entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepositry extends JpaRepository<UsersEntity,String> {
    Optional<UsersEntity> findByEmail(String username);
}
