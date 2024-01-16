package com.api.management.leave.leavemanagementapi.mapper;

import com.api.management.leave.leavemanagementapi.dto.LeaveDto;
import com.api.management.leave.leavemanagementapi.entity.Leave;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LeaveMapper {
    LeaveMapper INSTANCE = Mappers.getMapper(LeaveMapper.class);
    LeaveDto toDto (Leave leave);
    Leave toEntity(LeaveDto leaveDto);
}
