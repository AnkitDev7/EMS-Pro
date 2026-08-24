package com.example.Ems_Pro.Payload.Response;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponse {

    private String companyId;

    private String companyName;

    private String companyAddress;

    private String companyEmail;

    private String companyPhoneNo;
}
