package com.example.Ems_Pro.Entity;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;


@Entity
@Table(name = "department_location")
@IdClass(DepartmentLocationEntity.DepartmentLocationId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentLocationEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private DepartmentEntity department;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private LocationEntity location;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class DepartmentLocationId implements Serializable {
        private String department;  // matches Department PK type (departmentId)
        private Integer location;   // matches Location PK type (locationId)
    }
}