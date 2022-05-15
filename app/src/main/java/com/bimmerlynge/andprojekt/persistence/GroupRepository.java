package com.bimmerlynge.andprojekt.persistence;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bimmerlynge.andprojekt.model.Entry;
import com.bimmerlynge.andprojekt.model.Group;
import com.bimmerlynge.andprojekt.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GroupRepository {
    private static GroupRepository instance;
    private FirebaseDatabase database;
    private Group currentGroup;
    private String userId;

    private GroupRepository(){}

    public static synchronized GroupRepository getInstance(){
        if (instance == null)
            instance = new GroupRepository();
        return instance;
    }

    public void init(String userId){
        //dbRef = FirebaseDatabase.getInstance("https://andprojekt-2f098-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("users").child(userId);
        database = FirebaseDatabase.getInstance("https://andprojekt-2f098-default-rtdb.europe-west1.firebasedatabase.app");
        this.userId = userId;
        //dbRef = FirebaseDatabase.getInstance().getReference("message");
    }

    public void setGroup(Group group){
        currentGroup = group;
    }
//    public LiveData<List<Entry>> getGroupEntries(){
//        MutableLiveData<List<Entry>> mutableLiveData = new MutableLiveData<>();
//        List<Entry> entries = new ArrayList<>();
//        DatabaseReference ref = database.getReference().child("Entries").child(currentGroup.getId()).child(userId);
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User user = userById(userId, currentGroup);
//                for (DataSnapshot child : snapshot.getChildren()) {
//                    Entry entry = child.getValue(Entry.class);
//                    entries.add(entry);
//                    user.updateRemain(entry.getItemPrice());
//                }
//                DatabaseReference ref = database.getReference().child("Groups").child(currentGroup.getId());
//                ref.setValue(currentGroup);
//                mutableLiveData.postValue(entries);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        return mutableLiveData;
//    }


    public LiveData<Group> getGroupUpdates(){
        MutableLiveData<Group> toReturn = new MutableLiveData<>();
        final Group[] buffer = new Group[1];
        DatabaseReference ref = database.getReference().child("Groups").child(currentGroup.getId());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Group group = snapshot.getValue(Group.class);
                updateGroup(group);
                toReturn.postValue(group);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return toReturn;
    }

    public LiveData<Group> getCurrentGroup(){
        MutableLiveData<Group> group = new MutableLiveData<>();
        final Group[] buffer = new Group[1];
        String userId = FirebaseAuth.getInstance().getUid();
        DatabaseReference ref = database.getReference().child("Groups");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    Group data = child.getValue(Group.class);

                    if (data != null){
                        data.setId(child.getKey());
                        updateGroup(data);}
                    if (checkIfUserIsInGroup(userId, data))
                        //group.postValue(data);

                        buffer[0] = data;

                }
                //updateGroup(buffer[0]);
                group.postValue(buffer[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return group;
    }



    private void updateGroup(Group group){
        int remain = 0;
        for (User member : group.getMembers()) {
            remain += member.getRemain();
        }
        group.setRemain(remain);
    }

    private boolean checkIfUserIsInGroup(String userId, Group group){
        List<User> members = group.getMembers();
        for (User member : members) {
            if (userId.equals(member.getId()))
                return true;
        }
        return false;
    }

    public void createGroup(Group group){
        DatabaseReference ref = database.getReference().child("Groups");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ref.push().setValue(group);
        //dbRef.child("Users").child(user.getUid()).child("Groups").push().setValue(group);

    }

    public void updateGroupData(Group group) {
        updateGroup(group);
        DatabaseReference ref = database.getReference().child("Groups").child(group.getId());
        ref.setValue(group);
    }


//    public LiveData<List<Group>> getGroups() {
//        MutableLiveData<List<Group>> toReturn = new MutableLiveData<>();
//        List<Group> groups = new ArrayList<>();
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        DatabaseReference ref = database.getReference().child("Groups");
//
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren() ) {
//                    Group group = dataSnapshot.getValue(Group.class);
//                    if (group != null)
//                        group.setId(dataSnapshot.getKey());
//                    if (groupContainsUser(group, user.getUid()))
//                        groups.add(group);
//                }
//                toReturn.postValue(groups);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        return toReturn;
//    }

}
