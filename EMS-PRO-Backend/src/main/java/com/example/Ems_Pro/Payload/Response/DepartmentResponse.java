package com.example.Ems_Pro.Payload.Response;

import com.example.Ems_Pro.Entity.CompanyEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentResponse {

    private String departmentId;

    private String departmentName;

    private CompanyResponse company;

    private String status;
}
