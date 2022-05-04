package com.bimmerlynge.andprojekt.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bimmerlynge.andprojekt.model.Group;
import com.bimmerlynge.andprojekt.model.Member;

import java.util.ArrayList;

public class GroupViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private Group currentGroup;
    private ArrayList<Group> groupList;

    public GroupViewModel() {
        Log.i("mig", "Der laves en HomeViewModel nu");
        mText = new MutableLiveData<>();
        mText.setValue("This is home gay");
        currentGroup = null;
        groupList = new ArrayList(){
            {
                add(new Group(1,"Gruppe 1", 115));
                add(new Group(2,"Gruppe 2", 142));
                add(new Group(3,"Gruppe 3", 1154));

            }
        };
        Group group = groupList.get(0);
        group.addMember(new Member("Jens",250));
        group.addMember(new Member("Henning",433));
        group.addMember(new Member("Birg",15));
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setCurrentGroup(Group group){
        currentGroup = group;
    }

    public ArrayList<Group> getAllGroups(){
        return groupList;
    }

    public Group getCurrentGroup(){return currentGroup;}
}