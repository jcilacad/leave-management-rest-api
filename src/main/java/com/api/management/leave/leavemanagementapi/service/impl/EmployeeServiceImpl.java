package com.api.management.leave.leavemanagementapi.service.impl;

import com.api.management.leave.leavemanagementapi.dto.*;
import com.api.management.leave.leavemanagementapi.entity.Employee;
import com.api.management.leave.leavemanagementapi.entity.Leave;
import com.api.management.leave.leavemanagementapi.exception.ResourceNotFoundException;
import com.api.management.leave.leavemanagementapi.mapper.EmployeeMapper;
import com.api.management.leave.leavemanagementapi.repository.EmployeeRepository;
import com.api.management.leave.leavemanagementapi.repository.LeaveRepository;
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
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    private LeaveRepository leaveRepository;
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
        if (!employee) throw new ResourceNotFoundException(AppConstants.EMPLOYEE, "Id", id);
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
                }

                employee.setExcluded(AppConstants.DEFAULT_IS_EXCLUDED);
                employee.setRemainingForcedLeave(remVacationLeave.compareTo(AppConstants.FIVE) == 1
                        || remVacationLeave.compareTo(AppConstants.FIVE) == 0
                        ? AppConstants.FIVE
                        : remVacationLeave);
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
        EmployeeDto employeeDto = employeeMapper.toDto(employee);
        LeaveResponseDto leaveResponseDto = new LeaveResponseDto();
        leaveResponseDto.setEmployeeDto(employeeDto);
        return getLeaveResponseDto(employeeDto);
    }

    @Override
    @Transactional
    public LeaveResponseDto leaveRequest(Long id, LeaveRequestDto leaveRequestDto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.EMPLOYEE, "Id", id));
        String leaveType = leaveRequestDto.getLeaveType();
        LocalDate dateFrom = leaveRequestDto.getDateFrom();
        LocalDate dateTo = leaveRequestDto.getDateTo();
        BigDecimal daysRequested = leaveRequestDto.getDaysRequested();
        BigDecimal sickLeaveTotal = employee.getSickLeaveTotal();
        BigDecimal vacationLeaveTotal = employee.getVacationLeaveTotal();
        BigDecimal forcedLeave = employee.getRemainingForcedLeave();
        BigDecimal specialPrivilege = employee.getRemainingSpecialPrivilegeLeave();
        if (leaveType.equalsIgnoreCase(LeaveTypes.SICK.getLeave())) {
            BigDecimal diffSickLeaveDaysRequested = sickLeaveTotal.subtract(daysRequested);
            if (diffSickLeaveDaysRequested.signum() == -1) {
                employee.setSickLeaveTotal(AppConstants.ZERO);
                BigDecimal absoluteOfDifference = diffSickLeaveDaysRequested.abs();
                BigDecimal diffVacationLeaveAbsoluteDiff = vacationLeaveTotal.subtract(absoluteOfDifference);
                if (diffVacationLeaveAbsoluteDiff.signum() == -1) {
                    employee.setVacationLeaveTotal(AppConstants.ZERO);
                } else {
                    employee.setVacationLeaveTotal(diffVacationLeaveAbsoluteDiff);
                }

            } else {
                employee.setSickLeaveTotal(diffSickLeaveDaysRequested);
            }

        } else if (leaveType.equalsIgnoreCase(LeaveTypes.VACATION.getLeave())) {
            BigDecimal diffVacationLeaveDaysRequested = vacationLeaveTotal.subtract(daysRequested);
            if (diffVacationLeaveDaysRequested.compareTo(AppConstants.FIVE) == -1) {
                employee.setRemainingForcedLeave(diffVacationLeaveDaysRequested);
                if (diffVacationLeaveDaysRequested.signum() == -1) {
                    employee.setRemainingForcedLeave(AppConstants.ZERO);
                    employee.setVacationLeaveTotal(AppConstants.ZERO);
                } else {
                    employee.setVacationLeaveTotal(diffVacationLeaveDaysRequested);
                }
            }
        } else if (leaveType.equalsIgnoreCase(LeaveTypes.FORCED.getLeave())) {
            BigDecimal diffForcedLeaveDaysRequested = forcedLeave.subtract(daysRequested);
            BigDecimal diffVacationLeaveDaysRequested = vacationLeaveTotal.subtract(daysRequested);
            employee.setRemainingForcedLeave(diffForcedLeaveDaysRequested.signum() == -1
                    ? AppConstants.ZERO
                    : diffForcedLeaveDaysRequested);
            employee.setVacationLeaveTotal(diffVacationLeaveDaysRequested.signum() == -1
                    ? AppConstants.ZERO
                    : diffVacationLeaveDaysRequested);

        } else if (leaveType.equalsIgnoreCase(LeaveTypes.SPECIAL_PRIVILEGE.getLeave())) {
            BigDecimal diffSpecialPrivilegeLeaveDaysRequested = specialPrivilege.subtract(daysRequested);
            employee.setRemainingSpecialPrivilegeLeave(diffSpecialPrivilegeLeaveDaysRequested.signum() == -1
                    ? AppConstants.ZERO
                    : diffSpecialPrivilegeLeaveDaysRequested);
        }

        Leave leave = new Leave();
        leave.setEmployee(employee);
        leave.setForcedLeave(forcedLeave);
        leave.setSickLeave(sickLeaveTotal);
        leave.setSpecialPrivilegeLeave(specialPrivilege);
        leave.setVacationLeave(vacationLeaveTotal);
        leave.setLeaveType(leaveType);
        leave.setAppliedFrom(dateFrom.toString());
        leave.setAppliedTo(dateTo.toString());
        leave.setDaysRequested(daysRequested);
        leaveRepository.saveAndFlush(leave);
        Employee updatedEmployee = employeeRepository.save(employee);
        EmployeeDto employeeDto = employeeMapper.toDto(updatedEmployee);
        return getLeaveResponseDto(employeeDto);
    }

    private static LeaveResponseDto getLeaveResponseDto(EmployeeDto employeeDto) {
        LeaveResponseDto leaveResponseDto = new LeaveResponseDto();
        leaveResponseDto.setMessage("Leave request processed successfully.");
        leaveResponseDto.setEmployeeDto(employeeDto);
        leaveResponseDto.setLeaveTypes(Arrays.stream(LeaveTypes.values())
                .map(leaveType -> leaveType.getLeave())
                .collect(Collectors.toList()));
        BigDecimal availableForcedLeaveToCancel = employeeDto.getVacationLeaveTotal().subtract(employeeDto.getRemainingForcedLeave());
        availableForcedLeaveToCancel = availableForcedLeaveToCancel.signum() == -1
                ? AppConstants.ZERO
                : availableForcedLeaveToCancel;
        leaveResponseDto.setAvailableForcedLeaveToCancel(availableForcedLeaveToCancel);
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
}
