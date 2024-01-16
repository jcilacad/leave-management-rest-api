package com.api.management.leave.leavemanagementapi.dto;

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
    private BigDecimal remainingForcedLeave;
    private BigDecimal remainingSpecialPrivilegeLeave;
    private BigDecimal vacationLeaveTotal;
    private BigDecimal sickLeaveTotal;
    private BigDecimal leaveWithoutPayTotal;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private Set<LeaveDto> leaves;
    private String message;
}
