package com.api.management.leave.leavemanagementapi;

import com.api.management.leave.leavemanagementapi.utils.HourConversion;
import com.api.management.leave.leavemanagementapi.utils.LeaveCreditsEarned;
import com.api.management.leave.leavemanagementapi.utils.MinuteConversion;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootTest
class LeaveManagementApiApplicationTests {
	private static Logger logger = LoggerFactory.getLogger(LeaveManagementApiApplicationTests.class);

    @Test
    void contextLoads() {
    }

    @Test
    void getLeaveCreditsEarned() {
        LeaveCreditsEarned leaveCreditsEarned = Arrays
                .stream(LeaveCreditsEarned.values())
                .filter(e -> e.getDaysPresent().equals(BigDecimal.valueOf(4.00)))
                .findFirst().get();

        MinuteConversion minuteConversion = Arrays
                .stream(MinuteConversion.values())
                .filter(e -> e.getMinute() == 1)
                .findFirst().get();

        HourConversion hourConversion = Arrays
                .stream(HourConversion.values())
                .filter(e -> e.getHour() == 1)
                .findFirst().get();

		logger.info("Leave credits earned = {}", leaveCreditsEarned.getLeaveCreditsEarned());
		logger.info("Minute equivalent day = {}", minuteConversion.getEquivalentDay());
		logger.info("Hour equivalent day = {}", hourConversion.getEquivalentDay());
    }

}
