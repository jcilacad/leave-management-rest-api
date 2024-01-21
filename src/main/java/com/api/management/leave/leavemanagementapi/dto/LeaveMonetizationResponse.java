package com.api.management.leave.leavemanagementapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeaveMonetizationResponse {
    private BigDecimal forcedLeaveToCancel;
    private EmployeeDto employeeDto;
    private List<LeaveCreditsEarnedDto> leaveCredits;
}
