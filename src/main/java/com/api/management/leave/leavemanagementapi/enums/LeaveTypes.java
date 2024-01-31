package com.api.management.leave.leavemanagementapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LeaveTypes {
    VACATION("Vacation Leave"),
    FORCED("Mandatory / Forced Leave"),
    SICK("Sick Leave"),
    MATERNITY("Maternity Leave"),
    PATERNITY("Paternity Leave"),
    SPECIAL_PRIVILEGE("Special Privilege Leave"),
    SOLO_PARENT("Solo Parent Leave"),
    STUDY("Study Leave"),
    VAWC("10-Day VAWC Leave"),
    REHABILITATION_PRIVILEGE("Rehabilitation Privilege"),
    SPECIAL_LEAVE_BENEFITS_FOR_WOMEN("Special Leave Benefits for Women"),
    SPECIAL_EMERGENCY("Special Emergency Leave"),
    ADOPTION_LEAVE("Adoption Leave");

    private String leave;
}
