package com.example.Ems_Pro.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company")
public class CompanyEntity {

    @Id
    @Column(name = "company_id", length = 50)
    private String companyId;

    @Column(name = "company_name", length = 50)
    private String companyName;

    @Column(name = "company_address", length = 300)
    private String companyAddress;

    @Column(name = "company_email", length = 150)
    private String companyEmail;

    @Column(name = "company_phone_no", length = 50)
    private String companyPhoneNo;
}
