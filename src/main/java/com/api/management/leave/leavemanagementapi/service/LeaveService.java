package com.api.management.leave.leavemanagementapi.service;

import com.api.management.leave.leavemanagementapi.dto.LeaveRequestDto;
import com.api.management.leave.leavemanagementapi.dto.LeaveResponseDto;

public interface LeaveService {
    LeaveResponseDto getEmployeeByOfficialEmailOrEmployeeNumber(String query);
    LeaveResponseDto leaveRequest(Long id, LeaveRequestDto leaveRequestDto);
}