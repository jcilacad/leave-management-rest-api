package com.api.management.leave.leavemanagementapi.mapper;

import com.api.management.leave.leavemanagementapi.dto.EmployeeDto;
import com.api.management.leave.leavemanagementapi.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDto toDto(Employee employee);

    Employee toEntity(EmployeeDto employeeDto);
}
