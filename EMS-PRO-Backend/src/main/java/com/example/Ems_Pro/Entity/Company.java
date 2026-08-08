package com.example.Ems_Pro.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    private String companyId;

    @Column(nullable = false,unique = true)
    private String companyName;

    @Email
    private String email;

    private String phone;

    private String address;
}
