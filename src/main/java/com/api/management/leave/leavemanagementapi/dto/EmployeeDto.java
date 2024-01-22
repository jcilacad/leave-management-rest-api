package com.api.management.leave.leavemanagementapi.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

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
    private boolean excluded;
    @Column(precision = 10, scale = 3)
    private BigDecimal remainingForcedLeave;
    @Column(precision = 10, scale = 3)
    private BigDecimal remainingSpecialPrivilegeLeave;
    @Column(precision = 10, scale = 3)
    private BigDecimal vacationLeaveTotal;
    @Column(precision = 10, scale = 3)
    private BigDecimal sickLeaveTotal;
    @Column(precision = 10, scale = 3)
    private BigDecimal leaveWithoutPayTotal;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private Set<LeaveDto> leaves;
    private String message;
}
