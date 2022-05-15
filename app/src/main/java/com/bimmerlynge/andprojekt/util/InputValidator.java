package com.bimmerlynge.andprojekt.util;

import android.widget.EditText;

public class InputValidator {

    public static boolean checkCreateGroupInput(String groupName, String budgetPrPerson){
        return !groupName.trim().isEmpty() && !budgetPrPerson.trim().isEmpty();
    }


    public static boolean checkAddEntryInput(String item, String price) {
        return !item.trim().isEmpty() && !price.trim().isEmpty() && !price.trim().equals("0");
    }
}
