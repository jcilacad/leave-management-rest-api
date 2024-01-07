package com.api.management.leave.leavemanagementapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"employeeNumber", "officialEmail"})
})
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String employeeNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nameExtension;
    private String officialEmail;
    private BigDecimal remainingForcedLeave;
    private BigDecimal remainingSpecialPrivilegeLeave;
    private BigDecimal vacationLeaveTotal;
    private BigDecimal sickLeaveTotal;
    private BigDecimal leaveWithoutPay;
    @CreationTimestamp
    private LocalDateTime dateCreated;
    @UpdateTimestamp
    private LocalDateTime dateUpdated;

    public Employee() {
        this.remainingForcedLeave = BigDecimal.valueOf(5.00);
        this.remainingSpecialPrivilegeLeave = BigDecimal.valueOf(3.00);
        this.vacationLeaveTotal = BigDecimal.valueOf(0.0000);
        this.sickLeaveTotal = BigDecimal.valueOf(0.0000);
        this.leaveWithoutPay = BigDecimal.valueOf(0.0000);
    }
}
