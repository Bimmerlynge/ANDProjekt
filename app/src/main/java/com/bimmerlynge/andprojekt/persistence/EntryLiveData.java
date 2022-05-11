package com.bimmerlynge.andprojekt.persistence;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.bimmerlynge.andprojekt.model.Entry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EntryLiveData extends LiveData<List<Entry>> {

    private final ValueEventListener listener = new ValueEventListener() {
        List<Entry> entries = new ArrayList<>();
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot child : snapshot.getChildren()) {
                Entry entry = child.getValue(Entry.class);
                entries.add(entry);
                Log.i("mig", "Entry data changed");
            }
            setValue(entries);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };


    DatabaseReference ref;
    public EntryLiveData(DatabaseReference ref){
        this.ref = ref;
    }

    @Override
    protected void onActive() {
        super.onActive();
        ref.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        ref.removeEventListener(listener);
    }
}
