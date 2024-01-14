package com.api.management.leave.leavemanagementapi.repository;

import com.api.management.leave.leavemanagementapi.entity.Employee;
import com.api.management.leave.leavemanagementapi.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeRepositoryTest {
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeRepositoryTest(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Test
    @Transactional
    void getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        employees.forEach(System.out::println);
    }

    @Test
    @Transactional
    void findEmployeesByQuery() {
        String employeeNumber = "202011649";
        List<Employee> employees = employeeRepository.findEmployeesByQuery(employeeNumber);
        employees.forEach(System.out::println);
    }

    @Test
    @Transactional
    void findEmployeeById() {
        Long id = 1L;
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        System.out.println(employee);
    }

    @Test
    void deleteEmployeeById() {
        Long id = 6L;
        employeeRepository.deleteById(id);
    }

    @Test
    void updateEmployee() {
        Long id = 3L;
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        employee.setLastName("Dayap");
        employeeRepository.save(employee);
    }
}