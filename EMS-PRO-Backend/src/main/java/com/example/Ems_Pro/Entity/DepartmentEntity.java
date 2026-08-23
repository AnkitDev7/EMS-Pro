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
    @Column(name = "department_id", length = 100)
    private String departmentId;

    @Column(name = "department_name", length = 200, nullable = false)
    private String departmentName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity company;

    @Column(name = "status", length = 15, nullable = false)
    private String status;

}
