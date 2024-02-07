package com.api.management.leave.leavemanagementapi.constants;

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
    public static final boolean DEFAULT_IS_EXCLUDED = false;

    public static final BigDecimal ZERO = BigDecimal.valueOf(0.000);
    public static final BigDecimal THREE = BigDecimal.valueOf(3.00);
    public static final BigDecimal FIVE = BigDecimal.valueOf(5.00);
    public static final BigDecimal LEAVE_PER_HOUR = BigDecimal.valueOf(0.125);

    public static final String EMPLOYEE = "Employee";
    public static final String MONETIZATION = "Monetization";

    public static final String EXCEL_SHEET = "Leaves";
    public static final String EXCEL_HEADER_VALUES = "attachment; filename=leaves.xlsx";
    public static final String EXCEL_MEDIA_TYPE = "application/vnd.ms-excel";
    public static final String[] EXCEL_HEADERS = {
            "Id",
            "Leave Type",
            "Applied From",
            "Applied To",
            "Days Requested",
            "Vacation Leave",
            "Sick Leave",
            "Special Privilege Leave",
            "Forced Leave"
    };
}
