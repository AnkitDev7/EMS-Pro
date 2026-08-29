package com.example.Ems_Pro.ServiceIMPL;
import com.example.Ems_Pro.Entity.*;
import com.example.Ems_Pro.Payload.Request.DepartmentPayload;
import com.example.Ems_Pro.Payload.Response.DepartmentResponse;
import com.example.Ems_Pro.Repository.*;
import com.example.Ems_Pro.Service.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service
public class DepartmentServiceIMP implements DepartmentService {

    @Autowired
    private CompanyRepositry companyRepositry;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DepartmentRepositry departmentRepositry;

    @Autowired
    private UserRepositry userRepositry;

    @Override
    public DepartmentEntity createDepartment(DepartmentPayload departmentPayload) {

        Optional<CompanyEntity> company = companyRepositry
                .findById(departmentPayload.getCompanyId());

        if (company.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company Not Found");
        }

        DepartmentEntity departmentEntity = new DepartmentEntity();

        departmentEntity.setDepartmentId(
                departmentPayload.getDepartmentId()
        );

        departmentEntity.setDepartmentName(
                departmentPayload.getDepartmentName()
        );

        departmentEntity.setCompany(
                company.get()
        );

        departmentEntity.setStatus(
                departmentPayload.getStatus()
        );

        DepartmentEntity saveDepartment = departmentRepositry.save(departmentEntity);
        return saveDepartment;
    }

    @Override
    public DepartmentEntity updateDepartment(DepartmentPayload departmentPayload) {

        Optional<CompanyEntity> company = companyRepositry
                .findById(departmentPayload.getCompanyId());

        CompanyEntity company1 = company.get();

        if (company1 != null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company Not Found");
        }

        Optional<DepartmentEntity> departmentId = departmentRepositry
                .findById(departmentPayload.getDepartmentId());

        if (departmentId.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department Not Found");
        }

        DepartmentEntity departmentEntity = new DepartmentEntity();

        departmentEntity.setDepartmentId(
                departmentPayload.getDepartmentId()
        );

        departmentEntity.setDepartmentName(
                departmentPayload.getDepartmentName()
        );

        departmentEntity.setCompany(company1);

        departmentEntity.setStatus(
                departmentPayload.getStatus()
        );

        return departmentEntity;
    }

    @Override
    public DepartmentEntity deleteSingleDepartment(String departmentId) {

        Optional<DepartmentEntity> byId = departmentRepositry.findById(departmentId);

        DepartmentEntity departmentEntity = byId.get();

        if (departmentEntity != null){
            departmentRepositry.delete(departmentEntity);
            return departmentEntity;
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department Not Found");
    }

    @Override
    public List<DepartmentEntity> readAllDepartment() {

        List<DepartmentEntity> allDepartment = departmentRepositry.findAll();

        return allDepartment;
    }
}