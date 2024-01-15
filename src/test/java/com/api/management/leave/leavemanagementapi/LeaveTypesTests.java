package com.api.management.leave.leavemanagementapi;

import com.api.management.leave.leavemanagementapi.utils.LeaveTypes;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class LeaveTypesTests {
    private static Logger logger = LoggerFactory.getLogger(LeaveTypesTests.class);

    @Test
    void iterateLeaveTypes() {
        List<LeaveTypes> leaveTypes = Arrays.asList(LeaveTypes.values());
        leaveTypes.forEach(leaveType -> {
            logger.info("{} - {}", leaveType.getName(), leaveType.getLeave());
        });
    }
}
