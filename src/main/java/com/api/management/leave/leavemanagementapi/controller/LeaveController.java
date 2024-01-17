package com.api.management.leave.leavemanagementapi.controller;

import com.api.management.leave.leavemanagementapi.dto.LeaveRequestDto;
import com.api.management.leave.leavemanagementapi.dto.LeaveResponseDto;
import com.api.management.leave.leavemanagementapi.service.LeaveService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/leaves")
@AllArgsConstructor
public class LeaveController {
    private LeaveService leaveService;

    @GetMapping("/leaves")
    public ResponseEntity<LeaveResponseDto> getEmployeeByOfficialEmailOrEmployeeNumber(@RequestParam(name = "query", required = false) String query) {
        return ResponseEntity.ok(leaveService.getEmployeeByOfficialEmailOrEmployeeNumber(query));
    }

    @PostMapping("/{id}/leaves")
    public ResponseEntity<LeaveResponseDto> leaveRequest (
            @PathVariable Long id,
            @RequestBody @Valid LeaveRequestDto leaveRequestDto) {
        return new ResponseEntity<>(leaveService.leaveRequest(id, leaveRequestDto), HttpStatus.CREATED);
    }
}
