package com.api.management.leave.leavemanagementapi.utils;

import java.math.BigDecimal;

public class AppConstants {
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIR = "asc";
    public static final BigDecimal DEFAULT_FORCED_LEAVE = BigDecimal.valueOf(5.00);
    public static final BigDecimal DEFAULT_SPECIAL_PRIVILEGE_LEAVE = BigDecimal.valueOf(3.00);
    public static final BigDecimal DEFAULT_VACATION_LEAVE = BigDecimal.valueOf(0.000);
    public static final BigDecimal DEFAULT_SICK_LEAVE = BigDecimal.valueOf(0.000);
    public static final BigDecimal DEFAULT_LEAVE_WITHOUT_PAY = BigDecimal.valueOf(0.000);
}
