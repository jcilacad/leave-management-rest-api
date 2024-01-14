package com.api.management.leave.leavemanagementapi.repository;

import com.api.management.leave.leavemanagementapi.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e WHERE " +
            "e.employeeNumber LIKE CONCAT('%', :query, '%') OR " +
            "e.firstName LIKE CONCAT('%', :query, '%') OR " +
            "e.middleName LIKE CONCAT('%', :query, '%') OR " +
            "e.lastName LIKE CONCAT('%', :query, '%') OR " +
            "e.officialEmail LIKE CONCAT('%', :query, '%')")
    List<Employee> findEmployeesByQuery(String query);
    @Query("SELECT e FROM Employee e WHERE e IN :filteredEmployees")
    Page<Employee> findAllFiltered(List<Employee> filteredEmployees, Pageable pageable);
}
