package com.api.management.leave.leavemanagementapi.controller;

import com.api.management.leave.leavemanagementapi.dto.LeaveComputationDto;
import com.api.management.leave.leavemanagementapi.dto.LeaveRequestDto;
import com.api.management.leave.leavemanagementapi.dto.LeaveResponseDto;
import com.api.management.leave.leavemanagementapi.service.LeaveService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/leaves")
@AllArgsConstructor
public class LeaveController {
    private static Logger logger = LoggerFactory.getLogger(LeaveController.class);
    private LeaveService leaveService;

    @GetMapping("/request")
    public ResponseEntity<LeaveResponseDto> getEmployeeByOfficialEmailOrEmployeeNumber(
            @RequestParam(name = "query", required = false) String query) {
        return ResponseEntity.ok(leaveService.getEmployeeByOfficialEmailOrEmployeeNumber(query));
    }

    @PostMapping("/request")
    public ResponseEntity<LeaveResponseDto> leaveRequest(
            @RequestParam(name = "employeeId") Long employeeId,
            @RequestBody @Valid LeaveRequestDto leaveRequestDto) {
        return new ResponseEntity<>(leaveService.leaveRequest(employeeId, leaveRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/compute")
    public ResponseEntity<LeaveResponseDto> getInfoForComputation(
            @RequestParam(name = "query") String query) {
        return ResponseEntity.ok(leaveService.getInfoForComputation(query));
    }

    @PostMapping("/compute/{employeeId}")
    public ResponseEntity<LeaveResponseDto> computeLeaveCredits(
            @PathVariable(name = "employeeId") Long employeeId,
            @RequestBody @Valid LeaveComputationDto leaveComputationDto) {
        return new ResponseEntity<>(leaveService.computeLeaveCredits(employeeId, leaveComputationDto), HttpStatus.CREATED);
    }
}
