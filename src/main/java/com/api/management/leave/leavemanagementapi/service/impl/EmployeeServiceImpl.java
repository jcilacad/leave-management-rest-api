package com.api.management.leave.leavemanagementapi.service.impl;

import com.api.management.leave.leavemanagementapi.dto.EmployeeDto;
import com.api.management.leave.leavemanagementapi.dto.EmployeeResponse;
import com.api.management.leave.leavemanagementapi.dto.LeaveRequestDto;
import com.api.management.leave.leavemanagementapi.dto.LeaveResponseDto;
import com.api.management.leave.leavemanagementapi.entity.Employee;
import com.api.management.leave.leavemanagementapi.exception.ResourceNotFoundException;
import com.api.management.leave.leavemanagementapi.mapper.EmployeeMapper;
import com.api.management.leave.leavemanagementapi.repository.EmployeeRepository;
import com.api.management.leave.leavemanagementapi.service.EmployeeService;
import com.api.management.leave.leavemanagementapi.utils.AppConstants;
import com.api.management.leave.leavemanagementapi.utils.LeaveTypes;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    private EmployeeMapper employeeMapper;

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
        EmployeeDto employeeToDto = employeeMapper.toDto(savedEmployee);
        employeeToDto.setMessage("Employee created successfully.");
        return employeeToDto;
    }

    @Override
    @Transactional
    public EmployeeResponse getAllEmployees(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Employee> employees = employeeRepository.findAll(pageable);
        return setEmployeeResponse(employees);
    }

    @Override
    @Transactional
    public EmployeeResponse getEmployeesByQuery(String query, int pageNo, int pageSize, String sortBy, String sortDir) {
        List<Employee> filteredEmployees = employeeRepository.findEmployeesByQuery(query);
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Employee> employees = employeeRepository.findAllFiltered(filteredEmployees, pageable);
        return setEmployeeResponse(employees);
    }

    @Override
    @Transactional
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
        boolean employee = employeeRepository.existsById(id);
        if (!employee) {
            throw new ResourceNotFoundException(AppConstants.EMPLOYEE, "Id", id);
        }

        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeDto excludeEmployeeForcedLeave(Long id, boolean excluded) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.EMPLOYEE, "id", id));
        StringBuilder message = new StringBuilder();
        if (excluded) {
            if (employee.isExcluded()) {
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

    @Override
    @Transactional
    public EmployeeResponse resetLeaves(boolean reset, int pageNo, int pageSize, String sortBy, String sortDir) {
        if (reset) {
            List<Employee> employees = employeeRepository.findAll().stream().map(employee -> {
                BigDecimal remForcedLeave = employee.getRemainingForcedLeave();
                BigDecimal remVacationLeave = employee.getVacationLeaveTotal();
                if (!employee.isExcluded()) {
                    BigDecimal diffRemForcedAndVacation = remVacationLeave.subtract(remForcedLeave);
                    diffRemForcedAndVacation = diffRemForcedAndVacation.signum() == -1
                            ? AppConstants.ZERO
                            : diffRemForcedAndVacation;
                    employee.setVacationLeaveTotal(diffRemForcedAndVacation);
                    computeForcedLeave(employee);
                }

                employee.setExcluded(AppConstants.DEFAULT_IS_EXCLUDED);
                computeForcedLeave(employee);
                employee.setRemainingSpecialPrivilegeLeave(AppConstants.THREE);
                return employee;
            }).collect(Collectors.toList());
            List<Employee> savedEmployees = employeeRepository.saveAll(employees);
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                    ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
            Page<Employee> employeePage = employeeRepository.findAllFiltered(savedEmployees, pageable);
            EmployeeResponse response = setEmployeeResponse(employeePage);
            response.setMessage("Employee leaves successfully reset.");
            return response;
        } else {
            EmployeeResponse response = this.getAllEmployees(pageNo, pageSize, sortBy, sortDir);
            response.setMessage("Employee leaves failed to reset.");
            return response;
        }

    }

    @Override
    public LeaveResponseDto getEmployeeByOfficialEmailOrEmployeeNumber(String query) {
        Employee employee = employeeRepository.findEmployeeByEmailOrEmployeeNumber(query)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.EMPLOYEE, "query", query));
        EmployeeDto employeeDto =  employeeMapper.toDto(employee);
        LeaveResponseDto leaveResponseDto = new LeaveResponseDto();
        leaveResponseDto.setEmployeeDto(employeeDto);
        List<String> leaveTypes = Arrays.stream(LeaveTypes.values()).map(leaveType -> leaveType.getLeave()).collect(Collectors.toList());
        leaveResponseDto.setLeaveTypes(leaveTypes);
        BigDecimal availableForcedLeaveToCancel = employee.getVacationLeaveTotal().subtract(employee.getRemainingForcedLeave());
        availableForcedLeaveToCancel = availableForcedLeaveToCancel.signum() == -1
                ? AppConstants.ZERO
                : availableForcedLeaveToCancel;
        leaveResponseDto.setAvailableForcedLeaveToCancel(availableForcedLeaveToCancel);
        return leaveResponseDto;
    }

    @Override
    public LeaveResponseDto leaveRequest(Long id, LeaveRequestDto leaveRequestDto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.EMPLOYEE, "Id", id));

        EmployeeDto employeeDto = employeeMapper.toDto(employee);
        LeaveResponseDto leaveResponseDto = new LeaveResponseDto();
        leaveResponseDto.setEmployeeDto(employeeDto);

        // TODO: Process of Leave Request
        return leaveResponseDto;
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

    private void computeForcedLeave(Employee employee) {
        BigDecimal remVacationLeave = employee.getVacationLeaveTotal();
        employee.setRemainingForcedLeave(remVacationLeave.compareTo(AppConstants.FIVE) == 1
                || remVacationLeave.compareTo(AppConstants.FIVE) == 0
                ? AppConstants.FIVE
                : remVacationLeave);
    }
}
