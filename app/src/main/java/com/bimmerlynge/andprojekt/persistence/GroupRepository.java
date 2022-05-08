package com.bimmerlynge.andprojekt.persistence;

import android.util.Log;

import com.bimmerlynge.andprojekt.model.Group;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GroupRepository {
    private static GroupRepository instance;
    private DatabaseReference dbRef;
    private GroupLiveData group;

    private GroupRepository(){}

    public static synchronized GroupRepository getInstance(){
        if (instance == null)
            instance = new GroupRepository();
        return instance;
    }

    public void init(String userId){
        //dbRef = FirebaseDatabase.getInstance("https://andprojekt-2f098-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("users").child(userId);
        dbRef = FirebaseDatabase.getInstance("https://andprojekt-2f098-default-rtdb.europe-west1.firebasedatabase.app").getReference();
        //dbRef = FirebaseDatabase.getInstance().getReference("message");
    }

    public void createGroup(Group group){
        Log.i("mig", "før vi sender");
        dbRef.child("Groups").push().setValue(group);
        Log.i("mig", "burde have sendt");
        //dbRef.setValue(group);
    }
}
