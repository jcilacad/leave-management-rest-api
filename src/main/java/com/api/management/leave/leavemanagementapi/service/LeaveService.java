package com.api.management.leave.leavemanagementapi.service;

import com.api.management.leave.leavemanagementapi.dto.*;

public interface LeaveService {
    LeaveResponseDto getEmployeeByOfficialEmailOrEmployeeNumber(String query);
    LeaveResponseDto leaveRequest(Long id, LeaveRequestDto leaveRequestDto);
    LeaveResponseDto getInfoForComputation(String query);
    LeaveResponseDto computeLeaveCredits (Long employeeId, LeaveComputationDto leaveComputationDto);
    LeaveMonetizationResponse getInfoForMonetization(String query);
    LeaveMonetizationResponse monetizeLeaveCredits(Long employeeId, LeaveMonetizationRequest leaveMonetizationRequest);
}
