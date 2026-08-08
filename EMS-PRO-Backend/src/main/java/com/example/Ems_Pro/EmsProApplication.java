package com.example.Ems_Pro;

import com.example.Ems_Pro.Repository.UserRepositry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EmsProApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(EmsProApplication.class, args);
		UserRepositry userRepo = run.getBean(UserRepositry.class);

		PasswordEncoder bean = run.getBean(PasswordEncoder.class);
		String encode = bean.encode("SuperAdmin@123");
		System.out.println(encode);
	}

}
