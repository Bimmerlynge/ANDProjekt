package com.bimmerlynge.andprojekt.util;

import android.content.Context;
import android.util.Log;

import com.bimmerlynge.andprojekt.R;
import com.bimmerlynge.andprojekt.model.Entry;
import com.bimmerlynge.andprojekt.model.Group;
import com.github.mikephil.charting.data.BarEntry;

import java.text.ParseException;
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

    public static String categoryToProperResource(Context context, String category){
        String formatted = "";
        switch (category){
            case "Meat":
                formatted = context.getString(R.string.meat);
                break;
            case "Veggies":
                formatted = context.getString(R.string.veggies);
                break;
            case "Fruit":
                formatted = context.getString(R.string.fruit);
                break;
            case "Deli":
                formatted = context.getString(R.string.deli);
                break;
            case "Grains":
                formatted = context.getString(R.string.grains);
                break;
            case "Dairy":
                formatted = context.getString(R.string.dairy);
                break;
            case "Frozen":
                formatted = context.getString(R.string.frozen);
                break;
            case "Other":
                formatted = context.getString(R.string.other);
                break;
            default:
        }
        return formatted;
    }

    public static String yearMonthDayToDayMonthYear(String date){
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2= new SimpleDateFormat("dd-MM-yy");
        String toReturn = "";
        try {
            Date d = format1.parse(date);
            toReturn = format2.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return toReturn;
    }

    public static int getRemainIcon(Group group, double remain) {
        double max = group.getBudgetPerUser();
        double r = remain/max;
        if (remain == max)
            return R.drawable.ic_budget_full;
        else if (r < 1 && r >= 0.75)
            return R.drawable.ic_budget_partial_full;
        else if (r < 0.75 && r > 0.5)
            return R.drawable.ic_budget_half;
        else if (r < 0.5 && r > 0.25)
            return R.drawable.ic_budget_near_empty;
        else if (r < 0.25 && r > 0)
            return R.drawable.ic_budget_danger_close;
        else
            return R.drawable.ic_budget_empty;
    }
}
