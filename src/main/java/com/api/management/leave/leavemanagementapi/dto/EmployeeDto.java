package com.api.management.leave.leavemanagementapi.dto;

import com.api.management.leave.leavemanagementapi.entity.Leave;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Long id;
    @NotEmpty
    private String employeeNumber;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String middleName;
    @NotEmpty
    private String lastName;
    private String nameExtension;
    @NotEmpty
    @Email
    private String officialEmail;
    private BigDecimal remainingForcedLeave;
    private BigDecimal remainingSpecialPrivilegeLeave;
    private BigDecimal vacationLeaveTotal;
    private BigDecimal sickLeaveTotal;
    private BigDecimal leaveWithoutPayTotal;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private Set<Leave> leaves;

    public Long getId() {
        return id;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNameExtension() {
        return nameExtension;
    }

    public String getOfficialEmail() {
        return officialEmail;
    }

    public BigDecimal getRemainingForcedLeave() {
        return remainingForcedLeave;
    }

    public BigDecimal getRemainingSpecialPrivilegeLeave() {
        return remainingSpecialPrivilegeLeave;
    }

    public BigDecimal getVacationLeaveTotal() {
        return vacationLeaveTotal;
    }

    public BigDecimal getSickLeaveTotal() {
        return sickLeaveTotal;
    }

    public BigDecimal getLeaveWithoutPayTotal() {
        return leaveWithoutPayTotal;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public Set<Leave> getLeaves() {
        return leaves;
    }
}
