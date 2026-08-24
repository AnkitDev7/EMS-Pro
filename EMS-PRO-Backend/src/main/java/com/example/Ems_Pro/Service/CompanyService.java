package com.example.Ems_Pro.Service;

import com.example.Ems_Pro.Entity.CompanyEntity;
import com.example.Ems_Pro.Payload.Request.CompanyPayload;
import com.example.Ems_Pro.Payload.Response.CompanyResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface CompanyService {

    CompanyEntity createCompany(@Valid CompanyPayload companyPayload);

    CompanyEntity updateCompany(String companyId, @Valid CompanyPayload payload);

    void deleteCompany(String companyId);

    List<CompanyResponse> readAllCompany();
}
