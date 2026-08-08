package com.example.Ems_Pro.Service;
import com.example.Ems_Pro.Entity.Users;
import com.example.Ems_Pro.Repository.UserRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepositry userRepositry;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        Optional<Users> byUserEmail = userRepositry.findByEmail(userEmail);

        Users user = null;

        if (byUserEmail.isPresent()){
            user = byUserEmail.get();
            System.out.println("EMAIL = " + user.getEmail());
            System.out.println("PASSWORD = " + user.getPassword());
            System.out.println("ROLE = " + user.getRole().getRoleName());
        }else {
            throw new UsernameNotFoundException("User Not Found");
        }

        UserDetails userdetails = User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(String.valueOf(user.getRole().getRoleName().replace("ROLE_","")))
                .build();

        return userdetails;
    }
}
