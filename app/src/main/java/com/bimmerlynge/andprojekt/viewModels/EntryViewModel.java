package com.bimmerlynge.andprojekt.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.bimmerlynge.andprojekt.model.Entry;
import com.bimmerlynge.andprojekt.persistence.EntryRepository;
import com.bimmerlynge.andprojekt.util.DateParser;
import com.bimmerlynge.andprojekt.util.InputValidator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EntryViewModel extends AndroidViewModel {
    private EntryRepository entryRepository;

    public EntryViewModel(@NonNull Application application) {

        super(application);
        entryRepository = EntryRepository.getInstance();
    }

    public void addNewEntry(String itemName, String itemPrice, String category) {
        if (!InputValidator.checkAddEntryInput(itemName, itemPrice))
            throw new IllegalArgumentException();
        Entry newEntry = new Entry(itemName, Double.parseDouble(itemPrice), category, DateParser.getDateString());
        entryRepository.addNewEntry(newEntry);
    }
}
