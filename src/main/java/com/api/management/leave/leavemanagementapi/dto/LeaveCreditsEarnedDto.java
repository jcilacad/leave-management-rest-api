package com.api.management.leave.leavemanagementapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeaveCreditsEarnedDto {
    private BigDecimal daysPresent;
    private BigDecimal leaveWithoutPay;
    private BigDecimal LeaveCreditsEarned;
}
