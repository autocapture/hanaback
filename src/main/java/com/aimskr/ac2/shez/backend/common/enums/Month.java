package com.aimskr.ac2.kakao.backend.common.enums;

public enum Month {
    JAN("01", "Janurary"),
    FEB("02", "February"),
    MAR("03", "March"),
    APR("04", "April"),
    MAY("05", "May"),
    JUN("06", "June"),
    JUL("07", "July"),
    AUG("08", "August"),
    SEP("09", "September"),
    OCT("10", "October"),
    NOV("11", "November"),
    DEC("12", "December"),
    Jan("01", "Janurary"),
    Feb("02", "February"),
    Mar("03", "March"),
    Apr("04", "April"),
    May("05", "May"),
    Jun("06", "June"),
    Jul("07", "July"),
    Aug("08", "August"),
    Sep("09", "September"),
    Oct("10", "October"),
    Nov("11", "November"),
    Dec("12", "December");

    String monthDigit;
    String monthName;

    Month(String monthDigit, String monthName) {
        this.monthDigit = monthDigit;
        this.monthName = monthName;
    }

    public String getMonthDigit() {
        return monthDigit;
    }
    public String getMonthName() {
        return monthName;
    }
}
