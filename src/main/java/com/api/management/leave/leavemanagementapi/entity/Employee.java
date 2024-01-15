package com.api.management.leave.leavemanagementapi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"employeeNumber", "officialEmail"})
})
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_generator")
    @SequenceGenerator(name = "employee_generator", sequenceName = "employee_seq", allocationSize = 1)
    private Long id;
    @Column(nullable = false, unique = true)
    private String employeeNumber;
    @Column(nullable = false)
    private String firstName;
    private String middleName;
    @Column(nullable = false)
    private String lastName;
    private String nameExtension;
    @Column(nullable = false, unique = true)
    private String officialEmail;
    private boolean excluded;
    private BigDecimal remainingForcedLeave;
    private BigDecimal remainingSpecialPrivilegeLeave;
    private BigDecimal vacationLeaveTotal;
    private BigDecimal sickLeaveTotal;
    private BigDecimal leaveWithoutPayTotal;
    @CreationTimestamp
    private LocalDateTime dateCreated;
    @UpdateTimestamp
    private LocalDateTime dateUpdated;
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Leave> leaves;
}
