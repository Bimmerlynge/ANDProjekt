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


    public void setId(String id) {
        this.id = id;
    }

    public void setRemain(double remain) {
        this.remain = remain;
    }


    public void setName(String name) {
        this.name = name;
    }


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

    public double getBudgetPerUser() {
        return budgetPerUser;
    }

    public boolean checkGroupHasUserById(String userId){
        for (User member : members) {
            if (userId.equals(member.getId()))
                return true;
        }
        return false;
    }

    public void updateGroupRemain(){
        double remain = 0;
        for (User member : members) {
            remain += member.getRemain();
        }
        this.remain = remain;
    }

    public User getUserById(String id){
        for (User member : members) {
            if (member.getId().equals(id))
                return member;
        }
        return null;
    }



}


