package com.example.Ems_Pro.Entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "team",
        uniqueConstraints = @UniqueConstraint(columnNames = {"department_id", "team_name"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {

    @Id
    @Column(name = "team_id", length = 50)
    private String teamId;

    @Column(name = "team_name", length = 100, nullable = false)
    private String teamName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private DepartmentEntity department;

    @Column(name = "status", length = 20, nullable = false)
    private String status;
}
