package com.api.management.leave.leavemanagementapi.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeaveMonetizationRequest {
    @Pattern(regexp = "^[1-9]\\d*(\\.\\d+)?$", message = "Invalid input, provide only a number")
    BigDecimal vacationLeave;
    @Pattern(regexp = "^[1-9]\\d*(\\.\\d+)?$", message = "Invalid input, provide only a number")
    BigDecimal sickLeave;
}
