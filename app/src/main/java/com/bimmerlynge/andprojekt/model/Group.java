package com.bimmerlynge.andprojekt.model;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String name;
    private String id;
    private double budgetPerUser;
    private double remain;
    private List<User> members;
    //private List<Entry> entries;

    public Group(){}


//    public List<Entry> getEntries() {
//        return entries;
//    }

//    public void setEntries(List<Entry> entries) {
//        this.entries = entries;
//    }

    public Group(String name, double budgetPerUser) {
        this.name = name;
        this.budgetPerUser = budgetPerUser;
        remain = budgetPerUser;
        members = new ArrayList<>();
    }

    public Group(String name, String id, double budgetPerUser, double remain, List<User> members) {
        this.name = name;
        this.id = id;
        this.budgetPerUser = budgetPerUser;
        this.remain = remain;
        this.members = members;

    }
    public void setId(String id) {
        this.id = id;
    }

    public void setRemain(double remain) {
        this.remain = remain;
    }

    public void setBudgetPerUser(double budgetPerUser) {
        this.budgetPerUser = budgetPerUser;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMembers(List<User> members) {
        this.members = members;
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

    public void removeMemberById(int id){
        members.remove(id);
    }

    public double getBudgetPerUser() {
        return budgetPerUser;
    }



}


