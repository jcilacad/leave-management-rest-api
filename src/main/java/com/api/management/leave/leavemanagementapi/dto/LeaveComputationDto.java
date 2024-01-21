package com.api.management.leave.leavemanagementapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeaveComputationDto {
    @NotNull
    private int hour;
    @NotNull
    private int minute;
    @NotNull
    private BigDecimal daysPresent;
}
