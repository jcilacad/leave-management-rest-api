package com.api.management.leave.leavemanagementapi.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Long id;
    @NotEmpty
    private String employeeNumber;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String middleName;
    @NotEmpty
    private String lastName;
    private String nameExtension;
    @NotEmpty
    @Email
    private String officialEmail;
    private BigDecimal forcedLeave;
    private BigDecimal specialPrivilegeLeave;
    private BigDecimal vacationLeave;
    private BigDecimal sickLeave;
    private BigDecimal leaveWithoutPay;
}
