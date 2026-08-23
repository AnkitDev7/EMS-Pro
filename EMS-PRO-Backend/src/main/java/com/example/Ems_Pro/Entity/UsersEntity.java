package com.example.Ems_Pro.Entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "`user`")
public class UsersEntity {

    @Id
    @Column(name = "user_id", length = 100)
    private String userId;

    @Column(name = "user_created_at")
    private LocalDateTime userCreatedAt;

    @Column(name = "email", length = 150, nullable = false, unique = true)
    private String email;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "password", length = 200, nullable = false)
    private String password;

    @Column(name = "phone_no", length = 50, nullable = false, unique = true)
    private String phoneNo;

    @Column(name = "status", length = 50, nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private LocationEntity location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;

    @Column(name = "profile_image", length = 200)
    private String profileImage;
}