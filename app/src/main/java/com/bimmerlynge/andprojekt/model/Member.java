package com.bimmerlynge.andprojekt.model;

public class Member extends User{
    private double remain;

    public Member(String name, double remain){
        super(name);
        this.remain = remain;
    }

    public double getRemain(){return remain;}
}
