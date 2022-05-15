package com.bimmerlynge.andprojekt.model;

import com.google.firebase.database.ServerValue;

public class Entry {
    private String date;
    private String itemName;
    private double itemPrice;
    private String itemCategory;

    public Entry(){}

    public Entry(String itemName, double itemPrice, String itemCategory, String date) {
        this.date = date;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCategory = itemCategory;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }
}
