package com.example.Ems_Pro.ServiceIMPL;
import com.example.Ems_Pro.Entity.*;
import com.example.Ems_Pro.Payload.Request.DepartmentPayload;
import com.example.Ems_Pro.Repository.*;
import com.example.Ems_Pro.Service.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;


@Service
public class DepartmentServiceIMP implements DepartmentService {

    @Autowired
    private CompanyRepositry companyRepositry;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DepartmentRepositry departmentRepositry;

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

        return departmentEntity;

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
}