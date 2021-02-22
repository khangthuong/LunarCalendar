package com.example.lunarcalendar.model;

public class LunarDate {

    private final int day;
    private final int month;
    private final int year;
    private final int leap;
    private final int jd;
    private final int num_day;

    public LunarDate(int dd, int mm, int yy, int leap, int jd, int num_day) {
        this.day = dd;
        this.month = mm;
        this.year = yy;
        this.leap = leap;
        this.jd = jd;
        this.num_day = num_day;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getLeap() {
        return leap;
    }

    public int getJd() {
        return jd;
    }
    public int getNumday() {
        return num_day;
    }
}
