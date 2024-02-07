package com.api.management.leave.leavemanagementapi.constants;

public class PathConstants {

    // Auth Controller
    public static final String API_V1_AUTH = "/api/v1/auth";
    public static final String LOGIN = "/login";

    // Employee Controller
    public static final String API_V1_EMPLOYEES = "/api/v1/employees";
    public static final String ID = "/{id}";
    public static final String RESET_LEAVES = "/reset-leaves";
    public static final String EMPLOYEE_ID_EXCEL = "/{employeeId}/excel";

    // Leave Controller
    public static final String API_V1_LEAVES = "/api/v1/leaves";
    public static final String REQUEST = "/request";
    public static final String COMPUTE = "/compute";
    public static final String COMPUTE_EMPLOYEE_ID = "/compute/{employeeId}";
    public static final String MONETIZE = "/monetize";
    public static final String MONETIZE_EMPLOYEE_ID = "/monetize/{employeeId}";
}
