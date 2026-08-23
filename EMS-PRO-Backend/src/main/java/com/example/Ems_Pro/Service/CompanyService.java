package com.example.Ems_Pro.Service;

import com.example.Ems_Pro.Entity.CompanyEntity;
import com.example.Ems_Pro.Payload.Request.CompanyPayload;
import jakarta.validation.Valid;

public interface CompanyService {

    CompanyEntity createCompany(@Valid CompanyPayload companyPayload);

    CompanyEntity updateCompany(String companyId, @Valid CompanyPayload payload);

    void deleteCompany(String companyId);
}
