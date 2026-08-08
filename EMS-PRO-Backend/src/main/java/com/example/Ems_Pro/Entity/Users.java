package com.example.Ems_Pro.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`user`")
public class Users {

    @Id
    @Column(name = "user_Id")
    private String userId;
    private String name;
    private String email;
    private String password;
    private String phone_Number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_Id",nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_Id")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_Id")
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_Id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_Id")
    private Users manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_leader_Id")
    private Users teamLeader;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ACTIVE;

    @Column(name = "created_at",nullable = false,updatable = false)
    private LocalDateTime createdAt;

    public enum Status {
        ACTIVE,
        INACTIVE
    }

    @PrePersist
    protected void onCreate(){
        createdAt=LocalDateTime.now();
    }

}
