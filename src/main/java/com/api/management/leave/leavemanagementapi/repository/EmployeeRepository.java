package com.api.management.leave.leavemanagementapi.repository;

import com.api.management.leave.leavemanagementapi.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
