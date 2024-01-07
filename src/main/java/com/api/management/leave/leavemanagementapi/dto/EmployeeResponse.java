package com.api.management.leave.leavemanagementapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
    private List<EmployeeDto> content;
    private int pageNo;
    private  int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
