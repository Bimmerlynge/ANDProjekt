package com.bimmerlynge.andprojekt.persistence;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bimmerlynge.andprojekt.model.Entry;
import com.bimmerlynge.andprojekt.model.Group;
import com.bimmerlynge.andprojekt.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EntryRepository {
    private static EntryRepository instance;
    private FirebaseDatabase database;
    private Group currentGroup;
    private FirebaseUser currentUser;
    private DatabaseReference ref;
    private EntryLiveData entries;


    private EntryRepository(){}

    public static synchronized EntryRepository getInstance(){
        if (instance == null)
            instance = new EntryRepository();
        return instance;
    }

    public void init(){
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance("https://andprojekt-2f098-default-rtdb.europe-west1.firebasedatabase.app");
        //entries = new EntryLiveData(ref);
    }

    public void addNewEntry(Entry entry){
        DatabaseReference ref = database.getReference().child("Entries").child(currentGroup.getId()).child(currentUser.getUid());
        ref.push().setValue(entry);
        User user = userById(currentUser.getUid(),currentGroup);
        Log.i("mig", "Remain before update: " + user.getRemain());
        user.updateRemain(entry.getItemPrice());
        Log.i("mig", "After update: " + user.getRemain());
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
        //ref = database.getReference().child("Entries").child(currentGroup.getId()).child(currentUser.getUid());
    }

    public LiveData<List<Entry>> getEntries() throws NullPointerException{
       MutableLiveData<List<Entry>> liveData = new MutableLiveData<>();
        List<Entry> list = new ArrayList<>();

            DatabaseReference ref = database.getReference().child("Entries").child(currentGroup.getId()).child(currentUser.getUid());
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        Entry entry = child.getValue(Entry.class);
                        list.add(entry);
                    }
                    liveData.setValue(list);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        return liveData;}
}
