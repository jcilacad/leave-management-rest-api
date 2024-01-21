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
public class LeaveMonetizationResponse {
    private BigDecimal forcedLeaveToCancel;
    private EmployeeDto employeeDto;
}
