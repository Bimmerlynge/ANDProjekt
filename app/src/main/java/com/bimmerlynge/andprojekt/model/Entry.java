package com.bimmerlynge.andprojekt.model;


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


    public String getItemName() {
        return itemName;
    }


    public double getItemPrice() {
        return itemPrice;
    }


    public String getItemCategory() {
        return itemCategory;
    }


}
