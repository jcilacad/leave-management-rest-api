package com.api.management.leave.leavemanagementapi.controller;

import com.api.management.leave.leavemanagementapi.dto.*;
import com.api.management.leave.leavemanagementapi.service.EmployeeService;
import com.api.management.leave.leavemanagementapi.constants.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.api.management.leave.leavemanagementapi.constants.AppConstants.*;

@RestController
@RequestMapping("/api/v1/employees")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "Get all employees")
    @GetMapping
    public ResponseEntity<EmployeeResponse> getAllEmployees(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "pageNo", required = false, defaultValue = DEFAULT_PAGE_NUMBER) int pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = DEFAULT_SORT_BY) String sortBy,
            @RequestParam(name = "sortDir", required = false, defaultValue = DEFAULT_SORT_DIR) String sortDir) {
        return query == null || query.isBlank() || query.isEmpty()
                ? ResponseEntity.ok(employeeService.getAllEmployees(pageNo, pageSize, sortBy, sortDir))
                : ResponseEntity.ok(employeeService.getEmployeesByQuery(query, pageNo, pageSize, sortBy, sortDir));
    }

    @Operation(summary = "Create employee")
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        return new ResponseEntity<>(employeeService.createEmployee(employeeDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Get employee by id")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @Operation(summary = "Update employee")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto,
                                                      @PathVariable Long id) {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeDto, id));
    }

    @Operation(summary = "Delete employee")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully.");
    }

    @Operation(summary = "Exclude employee's forced leave")
    @PostMapping("/{id}")
    public ResponseEntity<EmployeeDto> excludeEmployeeForcedLeave(@PathVariable Long id,
                                                                  @RequestParam(name = "excluded", required = false) Boolean excluded) {
        return ResponseEntity.ok(employeeService.excludeEmployeeForcedLeave(id, excluded));
    }

    @Operation(summary = "Reset leaves")
    @PostMapping("/reset-leaves")
    public ResponseEntity<EmployeeResponse> resetLeaves (
            @RequestParam(name = "pageNo", required = false, defaultValue = DEFAULT_PAGE_NUMBER) int pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = DEFAULT_SORT_BY) String sortBy,
            @RequestParam(name = "sortDir", required = false, defaultValue = DEFAULT_SORT_DIR) String sortDir) {
        return ResponseEntity.ok(employeeService.resetLeaves(pageNo, pageSize, sortBy, sortDir));
    }

    @GetMapping("/{employeeId}/excel")
    public ResponseEntity<Resource> getFile(@PathVariable Long employeeId) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, EXCEL_HEADER_VALUES)
                .contentType(MediaType.parseMediaType(EXCEL_MEDIA_TYPE))
                .body(employeeService.load(employeeId));
    }
}
