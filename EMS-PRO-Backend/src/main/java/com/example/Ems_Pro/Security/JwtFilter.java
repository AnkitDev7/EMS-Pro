package com.example.Ems_Pro.Security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtGenrator jwtGenrator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Token Cheacking
        String header = request.getHeader("Authorization");
        System.out.println("header"+header);


        if (header != null && header.startsWith("Bearer ")) {
            // TOKEN PRESENT :- TOKEN CHEACKING HERE....
            String token = header.substring(7);

            // TOKEN EXPIRED
            if (!jwtGenrator.isExpired(token)){
                // TOKENISALIVE


                String userName = jwtGenrator.getUserName(token);

                List<String> allRoles = jwtGenrator.getRoles(token);

                ArrayList<SimpleGrantedAuthority> arrayList = new ArrayList<>();
                for (String role : allRoles) {
                    arrayList.add(new SimpleGrantedAuthority(role));
                }

                UsernamePasswordAuthenticationToken up =
                        new UsernamePasswordAuthenticationToken(userName, null, arrayList);

                SecurityContextHolder.getContext().setAuthentication(up);
            }

        }
        filterChain.doFilter(request,response);
    }
}
