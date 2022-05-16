package com.bimmerlynge.andprojekt.model;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Group {
    private String name;
    private String id;
    private int yearMonth;
    private double budgetPerUser;
    private double remain;
    private List<User> members;

    public Group(){}

    public Group(String name, double budgetPerUser, int yearMonth) {
        this.name = name;
        this.budgetPerUser = budgetPerUser;
        remain = budgetPerUser;
        members = new ArrayList<>();
        this.yearMonth = yearMonth;
    }

//    public Group(String name, String id, double budgetPerUser, double remain, List<User> members, int yearMonth) {
//        this.name = name;
//        this.id = id;
//        this.budgetPerUser = budgetPerUser;
//        this.remain = remain;
//        this.members = members;
//        this.yearMonth = yearMonth;
//
//    }
    public void newMonth(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMM");
        String string = dFormat.format(date);
        yearMonth = Integer.parseInt(string);
        for (User member : members) {
            member.setRemain(budgetPerUser);
        }
        remain = budgetPerUser*members.size();
    }

    public int getYearMonth() {
        return yearMonth;
    }

//    public void setYearMonth(int yearMonth) {
//        this.yearMonth = yearMonth;
//    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRemain(double remain) {
        this.remain = remain;
    }

//    public void setBudgetPerUser(double budgetPerUser) {
//        this.budgetPerUser = budgetPerUser;
//    }

    public void setName(String name) {
        this.name = name;
    }

//    public void setMembers(List<User> members) {
//        this.members = members;
//    }


    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public double getRemain() {
        return remain;
    }

    
    public List<User> getMembers() {
        return members;
    }

    public void addMember(User user){
        members.add(user);
    }

//    public void removeMemberById(int id){
//        members.remove(id);
//    }

    public double getBudgetPerUser() {
        return budgetPerUser;
    }



}


