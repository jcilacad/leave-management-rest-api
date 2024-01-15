package com.api.management.leave.leavemanagementapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequestDto {
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String middleName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String officialEmail;
    private String leaveType;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    @Pattern(regexp = "^[1-9]\\d*(\\.\\d+)?$", message = "Provide only a number.")
    private BigDecimal daysRequested;
    @Pattern(regexp = "^[1-9]\\d*(\\.\\d+)?$", message = "Provide only a number.")
    private BigDecimal leaveWithoutPay;
}
