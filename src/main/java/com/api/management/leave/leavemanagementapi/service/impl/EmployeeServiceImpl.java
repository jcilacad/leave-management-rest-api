package com.api.management.leave.leavemanagementapi.service.impl;

import com.api.management.leave.leavemanagementapi.dto.EmployeeDto;
import com.api.management.leave.leavemanagementapi.dto.EmployeeResponse;
import com.api.management.leave.leavemanagementapi.entity.Employee;
import com.api.management.leave.leavemanagementapi.exception.ResourceNotFoundException;
import com.api.management.leave.leavemanagementapi.mapper.EmployeeMapper;
import com.api.management.leave.leavemanagementapi.repository.EmployeeRepository;
import com.api.management.leave.leavemanagementapi.service.EmployeeService;
import com.api.management.leave.leavemanagementapi.utils.AppConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    private EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEntity(employeeDto);
        employee.setExcluded(AppConstants.DEFAULT_IS_EXCLUDED);
        employee.setRemainingForcedLeave(AppConstants.DEFAULT_FORCED_LEAVE);
        employee.setRemainingSpecialPrivilegeLeave(AppConstants.DEFAULT_SPECIAL_PRIVILEGE_LEAVE);
        employee.setVacationLeaveTotal(AppConstants.DEFAULT_VACATION_LEAVE);
        employee.setSickLeaveTotal(AppConstants.DEFAULT_SICK_LEAVE);
        employee.setLeaveWithoutPayTotal(AppConstants.DEFAULT_LEAVE_WITHOUT_PAY);
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toDto(savedEmployee);
    }

    @Override
    public EmployeeResponse getAllEmployees(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Employee> employees = employeeRepository.findAll(pageable);
        return setEmployeeResponse(employees);
    }

    @Override
    public EmployeeResponse getEmployeesByQuery(String query, int pageNo, int pageSize, String sortBy, String sortDir) {
        List<Employee> filteredEmployees = employeeRepository.findEmployeesByQuery(query);
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Employee> employees = employeeRepository.findAllFiltered(filteredEmployees, pageable);
        return setEmployeeResponse(employees);
    }

    private EmployeeResponse setEmployeeResponse(Page<Employee> employees) {
        List<EmployeeDto> content = employees.getContent().stream()
                .map(employee -> employeeMapper.toDto(employee))
                .collect(Collectors.toList());
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setContent(content);
        employeeResponse.setPageNo(employees.getNumber());
        employeeResponse.setPageSize(employees.getSize());
        employeeResponse.setTotalElements(employees.getTotalElements());
        employeeResponse.setTotalPages(employees.getTotalPages());
        employeeResponse.setLast(employees.isLast());
        return employeeResponse;
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        return employeeMapper.toDto(employee);
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto, Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        employee.setEmployeeNumber(employeeDto.getEmployeeNumber());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setMiddleName(employeeDto.getMiddleName());
        employee.setLastName(employeeDto.getLastName());
        employee.setNameExtension(employeeDto.getNameExtension());
        employee.setOfficialEmail(employeeDto.getOfficialEmail());
        employee.setRemainingForcedLeave(employeeDto.getRemainingForcedLeave());
        employee.setRemainingSpecialPrivilegeLeave(employeeDto.getRemainingSpecialPrivilegeLeave());
        employee.setVacationLeaveTotal(employeeDto.getVacationLeaveTotal());
        employee.setSickLeaveTotal(employeeDto.getSickLeaveTotal());
        employee.setLeaveWithoutPayTotal(employeeDto.getLeaveWithoutPayTotal());
        Employee updatedEmployee = employeeRepository.save(employee);
        return employeeMapper.toDto(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeDto excludeEmployeeForcedLeave(Long id, Boolean excluded) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        StringBuilder message = new StringBuilder();
        if (excluded) {
            if(employee.isExcluded()) {
                message.append("Employee was already excluded.");
            } else {
                employee.setExcluded(!AppConstants.DEFAULT_IS_EXCLUDED);
                message.append("Employee excluded successfully.");
            }
        }

        Employee updatedEmployee = employeeRepository.save(employee);
        EmployeeDto employeeDto = employeeMapper.toDto(updatedEmployee);
        employeeDto.setMessage(message.toString());
        return employeeDto;
    }
}
