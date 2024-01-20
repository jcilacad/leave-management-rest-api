package com.api.management.leave.leavemanagementapi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "leaves")
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leave_generator")
    @SequenceGenerator(name = "leave_generator", sequenceName = "leave_seq", allocationSize = 1)
    private Long id;
    private String leaveType;
    private String appliedFrom;
    private String appliedTo;
    private BigDecimal daysRequested;
    private BigDecimal vacationLeave;
    private BigDecimal sickLeave;
    private BigDecimal specialPrivilegeLeave;
    private BigDecimal forcedLeave;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    @CreationTimestamp
    private LocalDateTime dateCreated;
    @UpdateTimestamp
    private LocalDateTime dateUpdated;
}
