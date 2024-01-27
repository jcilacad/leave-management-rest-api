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

    @Column(precision = 10, scale = 3)
    private BigDecimal vacationLeave;

    @Column(precision = 10, scale = 3)
    private BigDecimal sickLeave;

    @Column(precision = 10, scale = 3)
    private BigDecimal specialPrivilegeLeave;

    @Column(precision = 10, scale = 3)
    private BigDecimal forcedLeave;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @UpdateTimestamp
    private LocalDateTime dateUpdated;
}
