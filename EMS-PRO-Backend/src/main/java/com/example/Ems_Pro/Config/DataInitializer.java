package com.example.Ems_Pro.Config;
import com.example.Ems_Pro.Entity.RoleEntity;
import com.example.Ems_Pro.Repository.RoleRepositry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initRoles(RoleRepositry roleRepository) {
        return args -> {

            createRoleIfNotExists(
                    roleRepository,
                    "ROLE001",
                    "SUPER_ADMIN"
            );

            createRoleIfNotExists(
                    roleRepository,
                    "ROLE002",
                    "ADMIN"
            );

            createRoleIfNotExists(
                    roleRepository,
                    "ROLE003",
                    "HR"
            );

            createRoleIfNotExists(
                    roleRepository,
                    "ROLE004",
                    "MANAGER"
            );

            createRoleIfNotExists(
                    roleRepository,
                    "ROLE005",
                    "TEAM_LEADER"
            );

            createRoleIfNotExists(
                    roleRepository,
                    "ROLE006",
                    "EMPLOYEE"
            );
        };
    }

    private void createRoleIfNotExists(
            RoleRepositry roleRepository,
            String roleId,
            String roleName) {

        if (!roleRepository.existsById(roleId)) {

            RoleEntity role = new RoleEntity();

            role.setRoleId(roleId);
            role.setRoleName(roleName);

            roleRepository.save(role);
        }
    }
}
