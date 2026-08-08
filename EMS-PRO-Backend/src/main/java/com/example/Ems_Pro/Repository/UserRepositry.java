package com.example.Ems_Pro.Repository;
import com.example.Ems_Pro.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepositry extends JpaRepository<Users,String> {
    Optional<Users> findByEmail(String username);
}
