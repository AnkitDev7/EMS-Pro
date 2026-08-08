package com.example.Ems_Pro.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @Column(name = "department_Id",nullable = false, unique = true)
    private String departmentId;

    @Column(nullable = false,unique = true)
    private String departmentName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_Id", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_Id", nullable = false)
    private Location location;

    @Column(nullable = false)
    private Boolean active = true;

}
