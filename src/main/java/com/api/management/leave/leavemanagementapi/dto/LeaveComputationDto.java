package com.api.management.leave.leavemanagementapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeaveComputationDto {
    @NotNull
    private HourConversionDto hourConversionDto;
    @NotNull
    private MinuteConversionDto minuteConversionDto;
    @NotNull
    private LeaveCreditsEarnedDto leaveCreditsEarnedDto;
}
