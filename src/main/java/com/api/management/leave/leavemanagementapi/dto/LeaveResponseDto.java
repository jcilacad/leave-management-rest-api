package com.api.management.leave.leavemanagementapi.dto;

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
    private String message;
    private EmployeeDto employeeDto;
    private List<String> leaveTypes;
    private List<HourConversionDto> hourConversions;
    private List<MinuteConversionDto> minuteConversions;
    private List<LeaveCreditsEarnedDto> leaveCreditsEarned;
    private BigDecimal availableForcedLeaveToCancel;
}
