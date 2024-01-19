package com.api.management.leave.leavemanagementapi.utils;

import java.math.BigDecimal;

public enum HourConversion {
    ONE(1, BigDecimal.valueOf(.125)),
    TWO(2, BigDecimal.valueOf(.250)),
    THREE(3, BigDecimal.valueOf(.375)),
    FOUR(4, BigDecimal.valueOf(.500)),
    FIVE(5, BigDecimal.valueOf(.625)),
    SIX(6, BigDecimal.valueOf(.750)),
    SEVEN(7, BigDecimal.valueOf(.875)),
    EIGHT(8, BigDecimal.valueOf(1.000));

    private final int hour;
    private final BigDecimal equivalentDay;

    HourConversion(int hour, BigDecimal equivalentDay) {
        this.hour = hour;
        this.equivalentDay = equivalentDay;
    }

    public int getHour() {
        return hour;
    }

    public BigDecimal getEquivalentDay() {
        return equivalentDay;
    }

    public String getName() {
        return name();
    }
}
