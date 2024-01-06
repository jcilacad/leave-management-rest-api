package com.api.management.leave.leavemanagementapi.utils;

public enum HourConversion {
    ONE(1, .125),
    TWO(2, .250),
    THREE(3, .375),
    FOUR(4, .500),
    FIVE(5, .625),
    SIX(6, .750),
    SEVEN(7, .875),
    EIGHT(8, 1.000);

    private final Integer hour;
    private final Double equivalentDay;

    HourConversion(Integer hour, Double equivalentDay) {
        this.hour = hour;
        this.equivalentDay = equivalentDay;
    }

    public Integer getHour() {
        return hour;
    }

    public Double getEquivalentDay() {
        return equivalentDay;
    }

    public String getName() {
        return name();
    }
}