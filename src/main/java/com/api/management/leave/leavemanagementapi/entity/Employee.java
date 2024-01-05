package com.api.management.leave.leavemanagementapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

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
    private BigDecimal forcedLeave;
    private BigDecimal specialPrivilegeLeave;
    private BigDecimal vacationLeave;
    private BigDecimal sickLeave;
    private BigDecimal leaveWithoutPay;
}
