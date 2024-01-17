package com.api.management.leave.leavemanagementapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeaveCreditsEarnedDto {
    private double daysPresent;
    private double leaveWithoutPay;
    private double LeaveCreditsEarned;
}
