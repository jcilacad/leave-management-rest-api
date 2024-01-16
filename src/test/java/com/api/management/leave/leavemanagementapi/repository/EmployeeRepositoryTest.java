package com.api.management.leave.leavemanagementapi.repository;

import com.api.management.leave.leavemanagementapi.entity.Employee;
import com.api.management.leave.leavemanagementapi.exception.ResourceNotFoundException;
import com.api.management.leave.leavemanagementapi.utils.LeaveTypes;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeRepositoryTest {
    private EmployeeRepository employeeRepository;
    private static Logger logger = LoggerFactory.getLogger(EmployeeRepositoryTest.class);

    @Autowired
    public EmployeeRepositoryTest(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

//    @Test
//    void getAllEmployees() {
//        List<Employee> employees = employeeRepository.findAll();
//        employees.forEach((employee) -> {
//            logger.info(String.valueOf(employee));
//        });
//    }
//
//    @Test
//    void findEmployeesByQuery() {
//        String employeeNumber = "202011649";
//        List<Employee> employees = employeeRepository.findEmployeesByQuery(employeeNumber);
//        employees.forEach((employee) -> {
//            logger.info(String.valueOf(employee));
//        });
//    }
//
//    @Test
//    void findEmployeeById() {
//        Long id = 1L;
//        Employee employee = employeeRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
//        logger.info(String.valueOf(employee));
//    }
//
//    @Test
//    void deleteEmployeeById() {
//        Long id = 6L;
//        employeeRepository.deleteById(id);
//    }
//
//    @Test
//    void updateEmployee() {
//        Long id = 3L;
//        Employee employee = employeeRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
//        employee.setLastName("Dayap");
//        employeeRepository.save(employee);
//    }

    @Test
    void test () {
        logger.info(String.valueOf(LocalDate.now()));
    }
}