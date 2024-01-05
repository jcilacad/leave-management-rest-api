package com.api.management.leave.leavemanagementapi.utils;

public enum MinuteConversion {
    ONE(1, .002),
    TWO(2, .004),
    THREE(3, .006),
    FOUR(4, .008),
    FIVE(5, .010),
    SIX(6, .012),
    SEVEN(7, .015),
    EIGHT(8, .017),
    NINE(9, .019),
    TEN(10, .021),
    ELEVEN(11, .023),
    TWELVE(12, .025),
    THIRTEEN(13, .027),
    FOURTEEN(14, .029),
    FIFTEEN(15, .031),
    SIXTEEN(16, .033),
    SEVENTEEN(17, .035),
    EIGHTEEN(18, .037),
    NINETEEN(19, .040),
    TWENTY(20, .042),
    TWENTY_ONE(21, .044),
    TWENTY_TWO(22, .046),
    TWENTY_THREE(23, .048),
    TWENTY_FOUR(24, .050),
    TWENTY_FIVE(25, .052),
    TWENTY_SIX(26, .054),
    TWENTY_SEVEN(27, .056),
    TWENTY_EIGHT(28, .058),
    TWENTY_NINE(29, .060),
    THIRTY(30, .062),
    THIRTY_ONE(31, .065),
    THIRTY_TWO(32, .067),
    THIRTY_THREE(33, .069),
    THIRTY_FOUR(34, .071),
    THIRTY_FIVE(35, .073),
    THIRTY_SIX(36, .075),
    THIRTY_SEVEN(37, .077),
    THIRTY_EIGHT(38, .079),
    THIRTY_NINE(39, .081),
    FORTY(40, .083),
    FORTY_ONE(41, .085),
    FORTY_TWO(42, .087),
    FORTY_THREE(43, .090),
    FORTY_FOUR(44, .092),
    FORTY_FIVE(45, .094),
    FORTY_SIX(46, .096),
    FORTY_SEVEN(47, .098),
    FORTY_EIGHT(48, .100),
    FORTY_NINE(49, .102),
    FIFTY(50, .104),
    FIFTY_ONE(51, .106),
    FIFTY_TWO(52, .108),
    FIFTY_THREE(53, .110),
    FIFTY_FOUR(54, .112),
    FIFTY_FIVE(55, .115),
    FIFTY_SIX(56, .117),
    FIFTY_SEVEN(57, .119),
    FIFTY_EIGHT(58, .121),
    FIFTY_NINE(59, .123),
    SIXTY(60, .125);

    private final Integer minute;
    private final Double equivalentDay;

    MinuteConversion(Integer minute, Double equivalentDay) {
        this.minute = minute;
        this.equivalentDay = equivalentDay;
    }

    public Integer getMinute() {
        return minute;
    }

    public Double getEquivalentDay() {
        return equivalentDay;
    }

    public String getName() {
        return name();
    }
}
