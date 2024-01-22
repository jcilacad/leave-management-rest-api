package com.api.management.leave.leavemanagementapi.service;

import com.api.management.leave.leavemanagementapi.dto.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}
