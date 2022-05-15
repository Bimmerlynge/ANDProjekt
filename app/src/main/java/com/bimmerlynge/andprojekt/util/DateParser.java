package com.bimmerlynge.andprojekt.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {

    public static int getYearMonthAsInt(){
        long today = System.currentTimeMillis();
        Date date = new Date(today);
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMM");
        String string = dFormat.format(date);
        return Integer.parseInt(string);
    }

    public static String getDateString(){
        long todayLong = System.currentTimeMillis();
        Date date = new Date(todayLong);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
