package com.api.management.leave.leavemanagementapi.controller;

import com.api.management.leave.leavemanagementapi.constants.PathConstants;
import com.api.management.leave.leavemanagementapi.dto.*;
import com.api.management.leave.leavemanagementapi.service.LeaveService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.api.management.leave.leavemanagementapi.constants.PathConstants.*;

@RestController
@RequestMapping(API_V1_LEAVES)
@AllArgsConstructor
public class LeaveController {

    private static Logger logger = LoggerFactory.getLogger(LeaveController.class);
    private final LeaveService leaveService;

    @Operation(summary = "Get employee by official email or employee number")
    @GetMapping(REQUEST)
    public ResponseEntity<LeaveResponseDto> getEmployeeByOfficialEmailOrEmployeeNumber(
            @RequestParam(name = "query", required = false) String query) {
        return ResponseEntity.ok(leaveService.getEmployeeByOfficialEmailOrEmployeeNumber(query));
    }

    @Operation(summary = "Leave request")
    @PostMapping(REQUEST)
    public ResponseEntity<LeaveResponseDto> leaveRequest(@RequestParam(name = "employeeId") Long employeeId,
                                                         @RequestBody @Valid LeaveRequestDto leaveRequestDto) {
        return new ResponseEntity<>(leaveService.leaveRequest(employeeId, leaveRequestDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Get information for leave computation")
    @GetMapping(COMPUTE)
    public ResponseEntity<LeaveResponseDto> getInfoForComputation(@RequestParam(name = "query") String query) {
        return ResponseEntity.ok(leaveService.getInfoForComputation(query));
    }

    @Operation(summary = "Compute leave credits")
    @PostMapping(COMPUTE_EMPLOYEE_ID)
    public ResponseEntity<LeaveResponseDto> computeLeaveCredits(
            @PathVariable(name = "employeeId") Long employeeId,
            @RequestBody @Valid LeaveComputationDto leaveComputationDto) {
        return new ResponseEntity<>(leaveService.computeLeaveCredits(employeeId, leaveComputationDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Get information for leave monetization")
    @GetMapping(MONETIZE)
    public ResponseEntity<LeaveMonetizationResponse> getInfoForMonetization(
            @RequestParam(name = "query") String query) {
        return ResponseEntity.ok(leaveService.getInfoForMonetization(query));
    }

    @Operation(summary = "Monetize leave credits")
    @PostMapping(MONETIZE_EMPLOYEE_ID)
    public ResponseEntity<LeaveMonetizationResponse> monetizeLeaveCredits(
            @PathVariable(name = "employeeId") Long employeeId,
            @RequestBody LeaveMonetizationRequest leaveMonetizationRequest) {
        return new ResponseEntity<>(leaveService.monetizeLeaveCredits(employeeId, leaveMonetizationRequest), HttpStatus.CREATED);
    }
}
