package com.api.management.leave.leavemanagementapi.controller;

import com.api.management.leave.leavemanagementapi.dto.JwtAuthResponse;
import com.api.management.leave.leavemanagementapi.dto.LoginDto;
import com.api.management.leave.leavemanagementapi.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.api.management.leave.leavemanagementapi.constants.PathConstants.API_V1_AUTH;
import static com.api.management.leave.leavemanagementapi.constants.PathConstants.LOGIN;

@AllArgsConstructor
@RestController
@RequestMapping(API_V1_AUTH)
public class AuthController {

    private final AuthService authService;

    @PostMapping(LOGIN)
    public ResponseEntity<JwtAuthResponse> authenticate(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }
}
