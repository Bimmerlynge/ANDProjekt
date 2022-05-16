package com.bimmerlynge.andprojekt.util;

import android.util.Log;

import com.bimmerlynge.andprojekt.model.Entry;
import com.github.mikephil.charting.data.BarEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Parser {

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

    public static String getLastYearMonth(){
        long today = System.currentTimeMillis();
        Date date = new Date(today);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH,-1);
        Date fin = c.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");

        return format.format(fin);

    }
    public static String getCurrentYearMonth(){
        long today = System.currentTimeMillis();
        Date date = new Date(today);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(date);
    }


    public static ArrayList<BarEntry> listToBarEntries(List<Entry> list){
        List<Double> data = new ArrayList<>();
        double meat =0, veggies = 0, fruit =0, deli = 0, grains = 0, dairy = 0,frozen =0,other =0;
        for (Entry entry : list) {
            String caseSubject = entry.getItemCategory();
            switch (caseSubject){
                case "Meat":
                    meat += entry.getItemPrice();
                    break;
                case "Veggies":
                    veggies += entry.getItemPrice();
                    break;
                case "Fruit":
                    fruit += entry.getItemPrice();
                    break;
                case "Deli":
                    deli += entry.getItemPrice();
                    break;
                case "Grains":
                    grains += entry.getItemPrice();
                    break;
                case "Dairy":
                    dairy += entry.getItemPrice();
                    break;
                case  "Frozen":
                    frozen += entry.getItemPrice();
                    break;
                case "Other":
                    other += entry.getItemPrice();
                    break;
                default:
            }
        }
        Collections.addAll(data, meat, veggies,fruit,deli,grains,dairy,frozen,other);
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < data.size(); i++){
            float item = data.get(i).floatValue();
            barEntries.add(new BarEntry((float) i, item));
        }
        return barEntries;
    }
}
