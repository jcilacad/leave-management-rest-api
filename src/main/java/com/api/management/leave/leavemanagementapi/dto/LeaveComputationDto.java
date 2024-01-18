package com.api.management.leave.leavemanagementapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeaveComputationDto {
    private HourConversionDto hourConversionDto;
    private MinuteConversionDto minuteConversionDto;
    private LeaveCreditsEarnedDto leaveCreditsEarnedDto;
}
