package com.example.Ems_Pro.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "location")
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_Id")
    private Integer locationId;

    @Column(nullable = false,length = 50)
    private String city;

    @Column(nullable = false,length = 300)
    private String address;

    @Column(nullable = false,length = 50)
    private  String state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_Id", nullable = false)
    private CompanyEntity company;
}
