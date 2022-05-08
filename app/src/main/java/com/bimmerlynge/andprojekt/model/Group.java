package com.bimmerlynge.andprojekt.model;

import java.util.ArrayList;

public class Group {
    private String name;
    private double budgetPerUser;
    private double remain;
    private ArrayList<Member> members;
    private ArrayList<Entry> entries;
    int id;



    public Group(String name, double budgetPerUser) {
        this.id = 0;
        this.name = name;
        this.budgetPerUser = budgetPerUser;
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

    public ArrayList<Member> getMembers() {
        return members;
    }

    public void addMember(Member user){
        members.add(user);
    }

    public void removeMemberById(int id){
        members.remove(id);
    }


    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", budgetPerUser=" + budgetPerUser +
                ", remain=" + remain +
                ", members=" + members +
                ", entries=" + entries +
                ", id=" + id +
                '}';
    }
}


