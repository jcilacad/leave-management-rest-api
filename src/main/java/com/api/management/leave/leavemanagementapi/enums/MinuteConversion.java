package com.api.management.leave.leavemanagementapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public enum MinuteConversion {
    ONE(1, BigDecimal.valueOf(.002)),
    TWO(2, BigDecimal.valueOf(.004)),
    THREE(3, BigDecimal.valueOf(.006)),
    FOUR(4, BigDecimal.valueOf(.008)),
    FIVE(5, BigDecimal.valueOf(.010)),
    SIX(6, BigDecimal.valueOf(.012)),
    SEVEN(7, BigDecimal.valueOf(.015)),
    EIGHT(8, BigDecimal.valueOf(.017)),
    NINE(9, BigDecimal.valueOf(.019)),
    TEN(10, BigDecimal.valueOf(.021)),
    ELEVEN(11, BigDecimal.valueOf(.023)),
    TWELVE(12, BigDecimal.valueOf(.025)),
    THIRTEEN(13, BigDecimal.valueOf(.027)),
    FOURTEEN(14, BigDecimal.valueOf(.029)),
    FIFTEEN(15, BigDecimal.valueOf(.031)),
    SIXTEEN(16, BigDecimal.valueOf(.033)),
    SEVENTEEN(17, BigDecimal.valueOf(.035)),
    EIGHTEEN(18, BigDecimal.valueOf(.037)),
    NINETEEN(19, BigDecimal.valueOf(.040)),
    TWENTY(20, BigDecimal.valueOf(.042)),
    TWENTY_ONE(21, BigDecimal.valueOf(.044)),
    TWENTY_TWO(22, BigDecimal.valueOf(.046)),
    TWENTY_THREE(23, BigDecimal.valueOf(.048)),
    TWENTY_FOUR(24, BigDecimal.valueOf(.050)),
    TWENTY_FIVE(25, BigDecimal.valueOf(.052)),
    TWENTY_SIX(26, BigDecimal.valueOf(.054)),
    TWENTY_SEVEN(27, BigDecimal.valueOf(.056)),
    TWENTY_EIGHT(28, BigDecimal.valueOf(.058)),
    TWENTY_NINE(29, BigDecimal.valueOf(.060)),
    THIRTY(30, BigDecimal.valueOf(.062)),
    THIRTY_ONE(31, BigDecimal.valueOf(.065)),
    THIRTY_TWO(32, BigDecimal.valueOf(.067)),
    THIRTY_THREE(33, BigDecimal.valueOf(.069)),
    THIRTY_FOUR(34, BigDecimal.valueOf(.071)),
    THIRTY_FIVE(35, BigDecimal.valueOf(.073)),
    THIRTY_SIX(36, BigDecimal.valueOf(.075)),
    THIRTY_SEVEN(37, BigDecimal.valueOf(.077)),
    THIRTY_EIGHT(38, BigDecimal.valueOf(.079)),
    THIRTY_NINE(39, BigDecimal.valueOf(.081)),
    FORTY(40, BigDecimal.valueOf(.083)),
    FORTY_ONE(41, BigDecimal.valueOf(.085)),
    FORTY_TWO(42, BigDecimal.valueOf(.087)),
    FORTY_THREE(43, BigDecimal.valueOf(.090)),
    FORTY_FOUR(44, BigDecimal.valueOf(.092)),
    FORTY_FIVE(45, BigDecimal.valueOf(.094)),
    FORTY_SIX(46, BigDecimal.valueOf(.096)),
    FORTY_SEVEN(47, BigDecimal.valueOf(.098)),
    FORTY_EIGHT(48, BigDecimal.valueOf(.100)),
    FORTY_NINE(49, BigDecimal.valueOf(.102)),
    FIFTY(50, BigDecimal.valueOf(.104)),
    FIFTY_ONE(51, BigDecimal.valueOf(.106)),
    FIFTY_TWO(52, BigDecimal.valueOf(.108)),
    FIFTY_THREE(53, BigDecimal.valueOf(.110)),
    FIFTY_FOUR(54, BigDecimal.valueOf(.112)),
    FIFTY_FIVE(55, BigDecimal.valueOf(.115)),
    FIFTY_SIX(56, BigDecimal.valueOf(.117)),
    FIFTY_SEVEN(57, BigDecimal.valueOf(.119)),
    FIFTY_EIGHT(58, BigDecimal.valueOf(.121)),
    FIFTY_NINE(59, BigDecimal.valueOf(.123)),
    SIXTY(60, BigDecimal.valueOf(.125));

    private final int minute;
    private final BigDecimal equivalentDay;
}
