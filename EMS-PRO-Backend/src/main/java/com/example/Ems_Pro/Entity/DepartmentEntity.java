package com.example.Ems_Pro.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "department")
public class DepartmentEntity {

    @Id
    @Column(name = "department_Id",nullable = false, unique = true)
    private String departmentId;

    @Column(nullable = false,unique = true)
    private String departmentName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_Id", nullable = false)
    private CompanyEntity company;

    @Column(nullable = false)
    private Boolean active = true;

}
