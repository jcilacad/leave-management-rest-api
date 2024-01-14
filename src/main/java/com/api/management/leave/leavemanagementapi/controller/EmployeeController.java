package com.api.management.leave.leavemanagementapi.controller;

import com.api.management.leave.leavemanagementapi.dto.EmployeeDto;
import com.api.management.leave.leavemanagementapi.dto.EmployeeResponse;
import com.api.management.leave.leavemanagementapi.service.EmployeeService;
import com.api.management.leave.leavemanagementapi.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<EmployeeResponse> getAllEmployees(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "pageNo", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = AppConstants.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(name = "sortDir", required = false, defaultValue = AppConstants.DEFAULT_SORT_DIR) String sortDir) {
        return query == null || query.isBlank() || query.isEmpty()
                ? ResponseEntity.ok(employeeService.getAllEmployees(pageNo, pageSize, sortBy, sortDir))
                : ResponseEntity.ok(employeeService.getEmployeesByQuery(query, pageNo, pageSize, sortBy, sortDir));
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        return new ResponseEntity<>(employeeService.createEmployee(employeeDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable Long id) {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully.");
    }

    @PostMapping("/{id}")
    public ResponseEntity<EmployeeDto> excludeEmployeeForcedLeave(
            @PathVariable Long id,
            @RequestParam(name = "excluded", required = false) Boolean excluded) {
        return ResponseEntity.ok(employeeService.excludeEmployeeForcedLeave(id, excluded));
    }

    @PostMapping("/leaves")
    public ResponseEntity<EmployeeResponse> resetLeaves (
            @RequestParam(name = "reset", required = false) boolean reset,
            @RequestParam(name = "pageNo", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = AppConstants.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(name = "sortDir", required = false, defaultValue = AppConstants.DEFAULT_SORT_DIR) String sortDir
    ) {
        return ResponseEntity.ok(employeeService.resetLeaves(reset, pageNo, pageSize, sortBy, sortDir));
    }
}
