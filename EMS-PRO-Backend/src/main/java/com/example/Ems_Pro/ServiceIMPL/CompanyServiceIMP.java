package com.example.Ems_Pro.ServiceIMPL;

import com.example.Ems_Pro.Entity.CompanyEntity;
import com.example.Ems_Pro.Payload.Request.CompanyPayload;
import com.example.Ems_Pro.Repository.CompanyRepositry;
import com.example.Ems_Pro.Service.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyServiceIMP implements CompanyService {

    @Autowired
    private CompanyRepositry companyRepositry;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CompanyEntity createCompany(CompanyPayload companyPayload) {

        CompanyEntity companyEntity = modelMapper
                .map(companyPayload, CompanyEntity.class);

        CompanyEntity saveCompany = companyRepositry.save(companyEntity);

        return saveCompany;
    }

    @Override
    public CompanyEntity updateCompany(
            String companyId,
            CompanyPayload payload) {

        CompanyEntity company =
                companyRepositry.findById(companyId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Company not found with ID: "
                                                + companyId
                                )
                        );

        // Company ID should not be changed
        company.setCompanyName(payload.getCompanyName());
        company.setCompanyAddress(payload.getCompanyAddress());
        company.setCompanyEmail(payload.getCompanyEmail());
        company.setCompanyPhoneNo(payload.getCompanyPhoneNo());

        return companyRepositry.save(company);
    }

    @Override
    public void deleteCompany(String companyId) {

        CompanyEntity company =
                companyRepositry.findById(companyId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Company not found with ID: "
                                                + companyId
                                )
                        );

        companyRepositry.delete(company);
    }
}
