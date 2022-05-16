package com.bimmerlynge.andprojekt.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bimmerlynge.andprojekt.model.Entry;
import com.bimmerlynge.andprojekt.persistence.EntryRepository;
import com.bimmerlynge.andprojekt.util.Parser;
import com.bimmerlynge.andprojekt.util.InputValidator;

import java.util.List;

public class EntryViewModel extends AndroidViewModel {
    private EntryRepository entryRepository;

    public EntryViewModel(@NonNull Application application) {

        super(application);
        entryRepository = EntryRepository.getInstance();
    }

    public void addNewEntry(String itemName, String itemPrice, String category) {
        if (!InputValidator.checkAddEntryInput(itemName, itemPrice))
            throw new IllegalArgumentException();
        Entry newEntry = new Entry(itemName, Double.parseDouble(itemPrice), category, Parser.getDateString());
        entryRepository.addNewEntry(newEntry);
    }

    public LiveData<List<Entry>> getEntriesByUser() {
        return entryRepository.getThisMonthsEntries();
    }

    public LiveData<List<Entry>> getGroupEntriesThisMonth() {
        return entryRepository.getGroupEntriesThisMonth();
    }
    public LiveData<List<Entry>> getGroupEntriesLastMonth() {
        return entryRepository.getGroupEntriesLastMonth();
    }
    public LiveData<List<Entry>> getEntriesByGroup() {
        return entryRepository.getAllEntries();
    }


    public LiveData<List<Entry>> getMyEntriesThisMonth() {
        return entryRepository.getThisMonthsEntries();
    }
}
