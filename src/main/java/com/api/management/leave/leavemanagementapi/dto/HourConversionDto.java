package com.api.management.leave.leavemanagementapi.dto;

import com.api.management.leave.leavemanagementapi.utils.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HourConversionDto {
    private int hour;
    private BigDecimal equivalentDay;
}
