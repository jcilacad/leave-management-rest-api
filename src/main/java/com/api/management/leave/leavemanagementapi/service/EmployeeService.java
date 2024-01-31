package com.api.management.leave.leavemanagementapi.service;

import com.api.management.leave.leavemanagementapi.dto.EmployeeDto;
import com.api.management.leave.leavemanagementapi.dto.EmployeeResponse;
import org.springframework.core.io.InputStreamResource;

import java.io.ByteArrayInputStream;

public interface EmployeeService {

    EmployeeDto createEmployee(EmployeeDto employeeDto);

    EmployeeResponse getAllEmployees(int pageNo, int pageSize, String sortBy, String sortDir);

    EmployeeResponse getEmployeesByQuery(String query, int pageNo, int pageSize, String sortBy, String sortDir);

    EmployeeDto getEmployeeById(Long id);

    EmployeeDto updateEmployee(EmployeeDto employeeDto, Long id);

    void deleteEmployee(Long id);

    EmployeeDto excludeEmployeeForcedLeave(Long id, boolean excluded);

    EmployeeResponse resetLeaves(int pageNo, int pageSize, String sortBy, String sortDir);

    InputStreamResource load(Long employeeId);
}
