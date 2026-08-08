package com.example.Ems_Pro;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.util.Base64;

@SpringBootTest
class EmsProApplicationTests {

	@Test
	void contextLoads() {
	}

/*	@Test
	void keyTest(){
		SecretKey build = Jwts.SIG.HS512.key().build();
		String finalKey = Base64.getEncoder().encodeToString(build.getEncoded());
		System.out.println("******************"+finalKey+"***********************");
	} */
}
