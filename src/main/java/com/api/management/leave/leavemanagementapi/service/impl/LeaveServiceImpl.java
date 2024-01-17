package com.api.management.leave.leavemanagementapi.service.impl;

import com.api.management.leave.leavemanagementapi.dto.EmployeeDto;
import com.api.management.leave.leavemanagementapi.dto.LeaveRequestDto;
import com.api.management.leave.leavemanagementapi.dto.LeaveResponseDto;
import com.api.management.leave.leavemanagementapi.entity.Employee;
import com.api.management.leave.leavemanagementapi.entity.Leave;
import com.api.management.leave.leavemanagementapi.exception.ResourceNotFoundException;
import com.api.management.leave.leavemanagementapi.mapper.EmployeeMapper;
import com.api.management.leave.leavemanagementapi.repository.EmployeeRepository;
import com.api.management.leave.leavemanagementapi.repository.LeaveRepository;
import com.api.management.leave.leavemanagementapi.service.LeaveService;
import com.api.management.leave.leavemanagementapi.utils.AppConstants;
import com.api.management.leave.leavemanagementapi.utils.LeaveTypes;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LeaveServiceImpl implements LeaveService {
    private EmployeeRepository employeeRepository;
    private LeaveRepository leaveRepository;
    private EmployeeMapper employeeMapper;

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
}
