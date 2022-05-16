package com.bimmerlynge.andprojekt.persistence;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bimmerlynge.andprojekt.model.Entry;
import com.bimmerlynge.andprojekt.model.Group;
import com.bimmerlynge.andprojekt.model.User;
import com.bimmerlynge.andprojekt.util.Parser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class EntryRepository {
    private static EntryRepository instance;
    private FirebaseDatabase database;
    private Group currentGroup;
    private FirebaseUser currentUser;

    private EntryRepository(){}

    public static synchronized EntryRepository getInstance(){
        if (instance == null)
            instance = new EntryRepository();
        return instance;
    }

    public void init(){
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance("https://andprojekt-2f098-default-rtdb.europe-west1.firebasedatabase.app");
        //entries = new EntryLiveData(ref); // For later improvements
        //entriesThisMonth = new EntryLiveData(ref);

    }


    public void addNewEntry(Entry entry){

        DatabaseReference ref = database.getReference().child("Entries").child(currentGroup.getId()).child(currentUser.getUid());
        ref.push().setValue(entry);
        User user = userById(currentUser.getUid(),currentGroup);
        user.updateRemain(entry.getItemPrice());
        DatabaseReference ref2 = database.getReference().child("Groups").child(currentGroup.getId());
        ref2.setValue(currentGroup);

    }

    private User userById(String id, Group group){
        for (User member : group.getMembers()) {
            if (member.getId().equals(id))
                return member;
        }
        return null;
    }
    public void setGroup(Group group) {
        currentGroup = group;
    }

    public LiveData<List<Entry>> getThisMonthsEntries(){
        MutableLiveData<List<Entry>> liveData = new MutableLiveData<>();
        List<Entry> list = new ArrayList<>();
        DatabaseReference ref = database.getReference().child("Entries").child(currentGroup.getId()).child(currentUser.getUid());
        ref.orderByChild("date").startAt(Parser.getCurrentYearMonth()).endAt(Parser.getCurrentYearMonth()+"\uf8ff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    Entry entry = child.getValue(Entry.class);
                    list.add(entry);
                }
                Collections.reverse(list);
                liveData.postValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return liveData;
    }
    public LiveData<List<Entry>> getGroupEntriesThisMonth(){
        MutableLiveData<List<Entry>> liveData = new MutableLiveData<>();
        List<Entry> list = new ArrayList<>();
        DatabaseReference ref = database.getReference().child("Entries").child(currentGroup.getId());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    for (DataSnapshot childChild : child.getChildren()) {
                        Entry entry = childChild.getValue(Entry.class);
                        if (entry.getDate().startsWith(Parser.getCurrentYearMonth())) {

                            list.add(entry);
                        }
                    }
                }
                liveData.postValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return liveData;
    }

    public LiveData<List<Entry>> getGroupEntriesLastMonth() {
        MutableLiveData<List<Entry>> liveData = new MutableLiveData<>();
        List<Entry> list = new ArrayList<>();
        DatabaseReference ref = database.getReference().child("Entries").child(currentGroup.getId());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    for (DataSnapshot childChild : child.getChildren()) {
                        Entry entry = childChild.getValue(Entry.class);
                        if (entry.getDate().startsWith(Parser.getLastYearMonth())) {
                            list.add(entry);
                        }
                    }

                }
                liveData.postValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return liveData;
    }


    public LiveData<List<Entry>> getAllEntries() {
        MutableLiveData<List<Entry>> liveData = new MutableLiveData<>();
        List<Entry> list = new ArrayList<>();
        DatabaseReference ref = database.getReference().child("Entries").child(currentGroup.getId());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    for (DataSnapshot childChild : child.getChildren()) {
                        Entry entry = childChild.getValue(Entry.class);
                        list.add(entry);
                    }

                }
                liveData.postValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return liveData;
    }


}
