package com.example.Ems_Pro.Payload.Request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthRequest {

    private String email;
    private String password;
}