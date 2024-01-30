package com.api.management.leave.leavemanagementapi.service;

import com.api.management.leave.leavemanagementapi.dto.JwtAuthResponse;
import com.api.management.leave.leavemanagementapi.dto.LoginDto;

public interface AuthService {

    JwtAuthResponse login(LoginDto loginDto);
}
