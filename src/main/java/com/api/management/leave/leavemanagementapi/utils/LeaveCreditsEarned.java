package com.api.management.leave.leavemanagementapi.utils;

import java.util.ArrayList;
import java.util.List;

public enum LeaveCreditsEarned {
    LCE1(30.00, 0.00, 1.250),
    LCE2(29.50, 0.50, 1.229),
    LCE3(29.00, 1.00, 1.208),
    LCE4(28.50, 1.50, 1.188),
    LCE5(28.00, 2.00, 1.167),
    LCE6(27.50, 2.50, 1.146),
    LCE7(27.00, 3.00, 1.125),
    LCE8(26.50, 3.50, 1.104),
    LCE9(26.00, 4.00, 1.083),
    LCE10(25.50, 4.50, 1.063),
    LCE11(25.00, 5.00, 1.042),
    LCE12(24.50, 5.50, 1.021),
    LCE13(24.00, 6.00, 1.000),
    LCE14(23.50, 6.50, .979),
    LCE15(23.00, 7.00, .958),
    LCE16(22.50, 7.50, .938),
    LCE17(22.00, 8.00, .917),
    LCE18(21.50, 8.50, .896),
    LCE19(21.00, 9.00, .875),
    LCE20(20.50, 9.50, .854),
    LCE21(20.00, 10.00, .833),
    LCE22(19.50, 10.50, .813),
    LCE23(19.00, 11.00, .792),
    LCE24(18.50, 11.50, .771),
    LCE25(18.00, 12.00, .750),
    LCE26(17.50, 12.50, .729),
    LCE27(17.00, 13.00, .708),
    LCE28(16.50, 13.50, .687),
    LCE29(16.00, 14.00, .667),
    LCE30(15.50, 14.50, .646),
    LCE31(15.00, 15.00, .625),
    LCE32(14.50, 15.50, .604),
    LCE33(14.00, 16.00, .583),
    LCE34(13.50, 16.50, .562),
    LCE35(13.00, 17.00, .542),
    LCE36(12.50, 17.50, .521),
    LCE37(12.00, 18.00, .500),
    LCE38(11.50, 18.50, .479),
    LCE39(11.00, 19.00, .458),
    LCE40(10.50, 19.50, .437),
    LCE41(10.00, 20.00, .417),
    LCE42(9.50, 20.50, .396),
    LCE43(9.00, 21.00, .375),
    LCE44(8.50, 21.50, .354),
    LCE45(8.00, 22.00, .333),
    LCE46(7.50, 22.50, .312),
    LCE47(7.00, 23.00, .292),
    LCE48(6.50, 23.50, .271),
    LCE49(6.00, 24.00, .250),
    LCE50(5.50, 24.50, .229),
    LCE51(5.00, 25.00, .208),
    LCE52(4.50, 25.50, .187),
    LCE53(4.00, 26.00, .167),
    LCE54(3.50, 26.50, .146),
    LCE55(3.00, 27.00, .125),
    LCE56(2.50, 27.50, .104),
    LCE57(2.00, 28.00, .083),
    LCE58(1.50, 28.50, .062),
    LCE59(1.00, 29.00, .042),
    LCE60(0.50, 29.50, .021),
    LCE61(0.00, 30.00, .000);

    private final double daysPresent;
    private final double leaveWithoutPay;
    private final double LeaveCreditsEarned;

    LeaveCreditsEarned(double daysPresent, double leaveWithoutPay, double leaveCreditsEarned) {
        this.daysPresent = daysPresent;
        this.leaveWithoutPay = leaveWithoutPay;
        this.LeaveCreditsEarned = leaveCreditsEarned;
    }

    public double getDaysPresent() {
        return daysPresent;
    }

    public double getLeaveWithoutPay() {
        return leaveWithoutPay;
    }

    public double getLeaveCreditsEarned() {
        return LeaveCreditsEarned;
    }

    public String getName() {
        return name();
    }
}
