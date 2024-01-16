package com.api.management.leave.leavemanagementapi.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequestDto {
    @NotEmpty
    private String leaveType;
    @NotNull
    private LocalDate dateFrom;
    @NotNull
    private LocalDate dateTo;
    @Digits(integer = 3, fraction = 2, message = "Days requested must be a numeric value with up to two decimal places")
    private BigDecimal daysRequested;
    @Digits(integer = 3, fraction = 2, message = "Leave without pay must be a numeric value with up to two decimal places")
    private BigDecimal leaveWithoutPay;
}
