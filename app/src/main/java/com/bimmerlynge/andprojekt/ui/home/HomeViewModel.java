package com.bimmerlynge.andprojekt.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bimmerlynge.andprojekt.model.Group;
import com.bimmerlynge.andprojekt.model.Member;
import com.bimmerlynge.andprojekt.model.User;
import com.bimmerlynge.andprojekt.persistence.GroupRepository;
import com.bimmerlynge.andprojekt.persistence.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class HomeViewModel extends AndroidViewModel {

    private Group currentGroup;
    private UserRepository userRepository;
    private GroupRepository groupRepository;


    public HomeViewModel(Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
        groupRepository = GroupRepository.getInstance();


    }

    public void init(){
        String userId = userRepository.getCurrentUser().getValue().getUid();
        groupRepository.init(userId);
    }

    public LiveData<FirebaseUser> getCurrentUser(){
        return userRepository.getCurrentUser();
    }



    public void setCurrentGroup(Group group){
        currentGroup = group;
    }

    public void CreateGroup(String groupName, String budgetPrPerson){
        if (!checkCreateGroupInput(groupName,budgetPrPerson))
            return;
        Group toCreate = new Group(groupName, Double.parseDouble(budgetPrPerson));
        groupRepository.createGroup(toCreate);

    }

    private boolean checkCreateGroupInput(String groupName, String budgetPrPerson){
        return !groupName.trim().isEmpty() && !budgetPrPerson.trim().isEmpty();
    }


    public Group getCurrentGroup(){return currentGroup;}

    public void signOut(){userRepository.signOut();}
}