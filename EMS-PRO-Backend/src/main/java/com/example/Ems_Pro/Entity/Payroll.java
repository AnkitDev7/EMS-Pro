package com.example.Ems_Pro.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
        name = "payroll",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "month"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payroll {

    @Id
    @Column(name = "payroll_id", length = 50)
    private String payrollId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity user;

    @Column(name = "salary", precision = 12, scale = 2)
    private BigDecimal salary;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "month", length = 100, nullable = false)
    private String month;

    @Column(name = "payment_status", length = 30)
    private String paymentStatus;
}