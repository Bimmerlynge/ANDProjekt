package com.bimmerlynge.andprojekt.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bimmerlynge.andprojekt.model.Entry;
import com.bimmerlynge.andprojekt.model.Group;
import com.bimmerlynge.andprojekt.model.User;
import com.bimmerlynge.andprojekt.persistence.EntryRepository;
import com.bimmerlynge.andprojekt.persistence.GroupRepository;
import com.bimmerlynge.andprojekt.persistence.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private Group currentGroup;
    private UserRepository userRepository;
    private GroupRepository groupRepository;
    private EntryRepository entryRepository;
    //private ArrayList<LiveData<Group>> groups;


    public HomeViewModel(Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
        groupRepository = GroupRepository.getInstance();
        entryRepository = EntryRepository.getInstance();


    }

    public void init(){
        String userId = userRepository.getCurrentUser().getValue().getUid();
        groupRepository.init(userId);
        entryRepository.init();
    }

    public LiveData<FirebaseUser> getCurrentUser(){
        return userRepository.getCurrentUser();
    }

    public void addNewEntry(String itemName, double itemPrice, String category){
        String userId = getCurrentUser().getValue().getUid();
        long todayLong = System.currentTimeMillis();
        Entry newEntry = new Entry(itemName, itemPrice, category, todayLong);
        entryRepository.addNewEntry(newEntry);
    }

    public void setCurrentGroup(Group group){
        currentGroup = group;
        entryRepository.setGroup(group);
        groupRepository.setGroup(group);
    }

    public void CreateGroup(String groupName, String budgetPrPerson){
        if (!checkCreateGroupInput(groupName,budgetPrPerson))
            return;

        Group toCreate = new Group(groupName, Double.parseDouble(budgetPrPerson));

        FirebaseUser currentUser = getCurrentUser().getValue();
        User creator = new User(currentUser.getUid(), currentUser.getDisplayName(), toCreate.getBudgetPerUser());
        toCreate.addMember(creator);
        groupRepository.createGroup(toCreate);

    }

    private boolean checkCreateGroupInput(String groupName, String budgetPrPerson){
        return !groupName.trim().isEmpty() && !budgetPrPerson.trim().isEmpty();
    }

//    public LiveData<List<Group>> getGroups(){
//        return groupRepository.getGroups();
//    }


    public LiveData<Group> getCurrentGroup(){

        return groupRepository.getCurrentGroup();}

    public void signOut(){userRepository.signOut();}

//    public LiveData<List<Group>> getAllGroups() {
//        return groupRepository.getGroups();
//    }

    public Group getGroup() {
        return currentGroup;
    }

    public LiveData<List<Entry>> getEntriesByUser() throws NullPointerException {
        return entryRepository.getEntries();
    }

    public void addUserToGroup(String id, String name) {
        User user = new User(id, name, currentGroup.getBudgetPerUser());
        currentGroup.addMember(user);
        groupRepository.addUserToGroup(currentGroup);
    }

    public LiveData<Group> getGroupUpdates() {
        return groupRepository.getGroupUpdates();
    }

//    public LiveData<List<Entry>> listenForEntryChanges() {
//       return groupRepository.getGroupEntries();
//    }
}