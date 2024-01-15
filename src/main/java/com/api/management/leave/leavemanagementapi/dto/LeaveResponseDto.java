package com.api.management.leave.leavemanagementapi.dto;

import com.api.management.leave.leavemanagementapi.utils.LeaveTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveResponseDto {
    private EmployeeDto employeeDto;
    private List<String> leaveTypes;
    private BigDecimal availableForcedLeaveToCancel;
}
