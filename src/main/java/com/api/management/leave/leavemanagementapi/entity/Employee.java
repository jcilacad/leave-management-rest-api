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
import java.util.Set;

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
    private BigDecimal leaveWithoutPayTotal;
    @CreationTimestamp
    private LocalDateTime dateCreated;
    @UpdateTimestamp
    private LocalDateTime dateUpdated;
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<Leave> leaves;
}
