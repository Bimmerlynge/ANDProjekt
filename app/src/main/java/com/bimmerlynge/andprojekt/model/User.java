package com.bimmerlynge.andprojekt.model;

public class User {
    private String id;
    private String name;
    private double remain;


    public User(){}
    public User(String id, String name, double remain) {
        this.name = name;
        this.id = id;
        this.remain = remain;
    }
    public void updateRemain(double amount){
        remain -= amount;
    }

    public double getRemain() {
        return remain;
    }

    public void setRemain(double remain) {
        this.remain = remain;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
