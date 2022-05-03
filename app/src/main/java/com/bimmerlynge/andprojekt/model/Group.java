package com.bimmerlynge.andprojekt.model;

import java.util.ArrayList;

public class Group {
    private String name;
    private double budget;
    private double remain;
    private ArrayList<User> members;
    private ArrayList<Entry> entries;



    public Group(String name, double budget) {
        this.name = name;
        this.budget = budget;
        remain = 0;
        members = new ArrayList<>();
        entries = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public double getRemain() {
        return remain;
    }

    public void setRemain(double remain) {
        this.remain = remain;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public void addMember(User user){
        members.add(user);
    }

    public void removeMemberById(int id){
        members.remove(id);
    }


}


