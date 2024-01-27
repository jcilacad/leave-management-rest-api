package com.api.management.leave.leavemanagementapi.service.impl;

import com.api.management.leave.leavemanagementapi.dto.*;
import com.api.management.leave.leavemanagementapi.entity.Employee;
import com.api.management.leave.leavemanagementapi.entity.Leave;
import com.api.management.leave.leavemanagementapi.exception.ResourceNotFoundException;
import com.api.management.leave.leavemanagementapi.mapper.EmployeeMapper;
import com.api.management.leave.leavemanagementapi.repository.EmployeeRepository;
import com.api.management.leave.leavemanagementapi.repository.LeaveRepository;
import com.api.management.leave.leavemanagementapi.service.LeaveService;
import com.api.management.leave.leavemanagementapi.utils.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LeaveServiceImpl implements LeaveService {

    private static Logger logger = LoggerFactory.getLogger(LeaveServiceImpl.class);
    private final EmployeeRepository employeeRepository;
    private final LeaveRepository leaveRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public LeaveResponseDto getEmployeeByOfficialEmailOrEmployeeNumber(String query) {
        Employee employee = employeeRepository.findEmployeeByEmailOrEmployeeNumber(query)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.EMPLOYEE, "query", query));
        EmployeeDto employeeDto = employeeMapper.toDto(employee);
        return getLeaveResponseDto(employeeDto);
    }

    @Override
    public LeaveResponseDto leaveRequest(Long id, LeaveRequestDto leaveRequestDto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.EMPLOYEE, "id", id));
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
                employee.setVacationLeaveTotal(diffVacationLeaveAbsoluteDiff.signum() == -1
                        ? AppConstants.ZERO
                        : diffVacationLeaveAbsoluteDiff);
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
        leaveResponseDto.setMessage("Success.");
        leaveResponseDto.setEmployeeDto(employeeDto);
        leaveResponseDto.setLeaveTypes(Arrays.stream(LeaveTypes.values())
                .map(LeaveTypes::getLeave)
                .collect(Collectors.toList()));
        BigDecimal forcedLeaveToCancel = forcedLeaveToCancel(employeeDto);
        leaveResponseDto.setAvailableForcedLeaveToCancel(forcedLeaveToCancel);
        return leaveResponseDto;
    }

    @Override
    public LeaveResponseDto getInfoForComputation(String query) {
        LeaveResponseDto leaveResponseDto = this.getEmployeeByOfficialEmailOrEmployeeNumber(query);
        List<HourConversion> hourConversions = List.of(HourConversion.values());
        List<MinuteConversion> minuteConversions = List.of(MinuteConversion.values());
        List<LeaveCreditsEarned> leaveCreditsEarned = List.of(LeaveCreditsEarned.values());
        List<HourConversionDto> hourConversionDto = hourConversions.stream()
                .map(hourConversion -> {
                    HourConversionDto hourConversionList = new HourConversionDto();
                    hourConversionList.setHour(hourConversion.getHour());
                    hourConversionList.setEquivalentDay(hourConversion.getEquivalentDay());
                    return hourConversionList;
                })
                .collect(Collectors.toList());
        List<MinuteConversionDto> minuteConversionDto = minuteConversions.stream()
                .map(minuteConversion -> {
                    MinuteConversionDto minuteConversionList = new MinuteConversionDto();
                    minuteConversionList.setMinute(minuteConversion.getMinute());
                    minuteConversionList.setEquivalentDay(minuteConversion.getEquivalentDay());
                    return minuteConversionList;
                })
                .collect(Collectors.toList());
        List<LeaveCreditsEarnedDto> leaveCreditsEarnedDto = leaveCreditsEarned.stream()
                .map(leaveCredits -> {
                    LeaveCreditsEarnedDto leaveCreditsEarnedDtoList = new LeaveCreditsEarnedDto();
                    leaveCreditsEarnedDtoList.setLeaveCreditsEarned(leaveCredits.getLeaveCreditsEarned());
                    leaveCreditsEarnedDtoList.setLeaveWithoutPay(leaveCredits.getLeaveWithoutPay());
                    leaveCreditsEarnedDtoList.setDaysPresent(leaveCredits.getDaysPresent());
                    return leaveCreditsEarnedDtoList;
                })
                .collect(Collectors.toList());
        leaveResponseDto.setHourConversions(hourConversionDto);
        leaveResponseDto.setMinuteConversions(minuteConversionDto);
        leaveResponseDto.setLeaveCreditsEarned(leaveCreditsEarnedDto);
        return leaveResponseDto;
    }

    @Override
    @Transactional
    public LeaveResponseDto computeLeaveCredits(Long employeeId, LeaveComputationDto leaveComputationDto) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.EMPLOYEE, "id", employeeId));
        LeaveCreditsEarned leaveCredits = Arrays
                .stream(LeaveCreditsEarned.values())
                .filter(e -> e.getDaysPresent().compareTo(leaveComputationDto.getDaysPresent()) == 0)
                .findFirst().orElseThrow();
        HourConversion hourConversions = Arrays
                .stream(HourConversion.values())
                .filter(hourConversion -> hourConversion.getHour() == leaveComputationDto.getHour())
                .findFirst().orElseThrow();
        MinuteConversion minuteConversions = Arrays
                .stream(MinuteConversion.values())
                .filter(minuteConversion -> minuteConversion.getMinute() == leaveComputationDto.getMinute())
                .findFirst().orElseThrow();
        BigDecimal leaveCreditsEarned = leaveCredits.getLeaveCreditsEarned();
        BigDecimal sickLeaveTotal = employee.getSickLeaveTotal();
        BigDecimal vacationLeaveTotal = employee.getVacationLeaveTotal();
        BigDecimal remainingForcedLeave = employee.getRemainingForcedLeave();
        BigDecimal leaveWithoutPay = employee.getLeaveWithoutPayTotal();
        sickLeaveTotal = sickLeaveTotal.add(leaveCreditsEarned);
        vacationLeaveTotal = vacationLeaveTotal.add(leaveCreditsEarned);
        remainingForcedLeave = remainingForcedLeave.add(leaveCreditsEarned);
        employee.setSickLeaveTotal(sickLeaveTotal);
        employee.setVacationLeaveTotal(vacationLeaveTotal);
        employee.setRemainingForcedLeave((remainingForcedLeave.compareTo(AppConstants.FIVE) == 1
                        || remainingForcedLeave.compareTo(AppConstants.FIVE) == 0)
                        ? AppConstants.FIVE : remainingForcedLeave);
        BigDecimal hoursLate = new BigDecimal(hourConversions.getHour());
        hoursLate = hoursLate.multiply(AppConstants.LEAVE_PER_HOUR);
        hoursLate = hoursLate.add(minuteConversions.getEquivalentDay());
        leaveWithoutPay = leaveWithoutPay.subtract(hoursLate);
        leaveWithoutPay = leaveWithoutPay.signum() == -1 ? AppConstants.ZERO : leaveWithoutPay;
        employee.setLeaveWithoutPayTotal(leaveWithoutPay);
        Leave leave = new Leave();
        leave.setEmployee(employee);
        leave.setVacationLeave(vacationLeaveTotal);
        leave.setSickLeave(sickLeaveTotal);
        leaveRepository.saveAndFlush(leave);
        Employee savedEmployee = employeeRepository.saveAndFlush(employee);
        employee.setSickLeaveTotal(savedEmployee.getSickLeaveTotal());
        employee.setVacationLeaveTotal(savedEmployee.getVacationLeaveTotal());
        employee.setRemainingForcedLeave(savedEmployee.getRemainingForcedLeave());
        savedEmployee = employeeRepository.saveAndFlush(employee);
        return this.getInfoForComputation(savedEmployee.getOfficialEmail());
    }

    @Override
    public LeaveMonetizationResponse getInfoForMonetization(String query) {
        Employee employee = employeeRepository.findEmployeeByEmailOrEmployeeNumber(query)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.EMPLOYEE, "query", query));
        BigDecimal forcedLeaveToCancel = forcedLeaveToCancel(employeeMapper.toDto(employee));
        LeaveMonetizationResponse leaveMonetizationResponse = new LeaveMonetizationResponse();
        leaveMonetizationResponse.setEmployeeDto(employeeMapper.toDto(employee));
        leaveMonetizationResponse.setForcedLeaveToCancel(forcedLeaveToCancel);
        return leaveMonetizationResponse;
    }

    @Override
    @Transactional
    public LeaveMonetizationResponse monetizeLeaveCredits(Long employeeId, LeaveMonetizationRequest leaveMonetizationRequest) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.EMPLOYEE, "id", employeeId));
        BigDecimal vacationLeave = leaveMonetizationRequest.getVacationLeave();
        BigDecimal sickLeave = leaveMonetizationRequest.getSickLeave();
        BigDecimal vacationLeaveTotal = employee.getVacationLeaveTotal();
        BigDecimal sickLeaveTotal = employee.getSickLeaveTotal();
        vacationLeaveTotal = vacationLeaveTotal.subtract(vacationLeave);
        sickLeaveTotal = sickLeaveTotal.subtract(sickLeave);
        vacationLeaveTotal = vacationLeaveTotal.signum() == -1 ? AppConstants.ZERO : vacationLeaveTotal;
        sickLeaveTotal = sickLeaveTotal.signum() == -1 ? AppConstants.ZERO : sickLeaveTotal;
        employee.setVacationLeaveTotal(vacationLeaveTotal);
        employee.setSickLeaveTotal(sickLeaveTotal);
        Employee savedEmployee = employeeRepository.saveAndFlush(employee);
        Leave leave = new Leave();
        leave.setLeaveType(AppConstants.MONETIZATION);
        leave.setSpecialPrivilegeLeave(savedEmployee.getRemainingSpecialPrivilegeLeave());
        leave.setForcedLeave(savedEmployee.getRemainingForcedLeave());
        leave.setVacationLeave(savedEmployee.getVacationLeaveTotal());
        leave.setSickLeave(savedEmployee.getSickLeaveTotal());
        leave.setEmployee(savedEmployee);
        leave.setAppliedFrom(String.format("Vacation Leave - %s", vacationLeave));
        leave.setAppliedTo(String.format("Sick Leave - %s", sickLeave));
        leaveRepository.saveAndFlush(leave);
        EmployeeDto employeeDto = employeeMapper.toDto(savedEmployee);
        BigDecimal forcedLeaveToCancel = forcedLeaveToCancel(employeeDto);
        LeaveMonetizationResponse leaveMonetizationResponse = new LeaveMonetizationResponse();
        leaveMonetizationResponse.setForcedLeaveToCancel(forcedLeaveToCancel);
        leaveMonetizationResponse.setEmployeeDto(employeeDto);
        return leaveMonetizationResponse;
    }

    private static BigDecimal forcedLeaveToCancel(EmployeeDto employeeDto) {
        BigDecimal availableForcedLeaveToCancel = employeeDto.getVacationLeaveTotal()
                .subtract(employeeDto.getRemainingForcedLeave());
        availableForcedLeaveToCancel = availableForcedLeaveToCancel.signum() == -1
                ? AppConstants.ZERO
                : availableForcedLeaveToCancel;
        return availableForcedLeaveToCancel;
    }
}
