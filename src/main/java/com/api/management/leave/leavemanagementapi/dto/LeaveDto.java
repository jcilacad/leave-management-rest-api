package com.api.management.leave.leavemanagementapi.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeaveDto {
    private Long id;
    @NotNull
    private String leaveType;
    @NotNull
    private String appliedFrom;
    @NotNull
    private String appliedTo;
    @Digits(integer = 3, fraction = 2, message = "Days requested must be a numeric value with up to two decimal places")
    private BigDecimal daysRequested;
    @Digits(integer = 3, fraction = 2, message = "Vacation leave must be a numeric value with up to two decimal places")
    private BigDecimal vacationLeave;
    @Digits(integer = 3, fraction = 2, message = "Sick leave must be a numeric value with up to two decimal places")
    private BigDecimal sickLeave;
    @Digits(integer = 3, fraction = 2, message = "Special privilege leave must be a numeric value with up to two decimal places")
    private BigDecimal specialPrivilegeLeave;
    @Digits(integer = 3, fraction = 2, message = "Forced leave must be a numeric value with up to two decimal places")
    private BigDecimal forcedLeave;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}

