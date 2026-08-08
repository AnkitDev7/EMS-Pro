package com.example.Ems_Pro.Controller;

import com.example.Ems_Pro.Payload.Request.JwtAuthRequest;
import com.example.Ems_Pro.Security.JwtGenrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtGenrator jwtGenrator;

    @PostMapping("/login")
    public String login(@RequestBody JwtAuthRequest jwtAuthRequest){

        Authentication auth = new UsernamePasswordAuthenticationToken(jwtAuthRequest.getEmail(),
                jwtAuthRequest.getPassword());

        Authentication getAuth = authenticationManager.authenticate(auth);
        UserDetails userDetails = (UserDetails) getAuth.getPrincipal();

        String token = jwtGenrator.GenerateToken(userDetails);

        return token;
    }

    @GetMapping("/sinup")
    public String sinup(){
        return "Sinup Your Account";
    }
}
