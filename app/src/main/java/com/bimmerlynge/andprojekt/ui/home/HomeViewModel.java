package com.bimmerlynge.andprojekt.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bimmerlynge.andprojekt.model.Entry;
import com.bimmerlynge.andprojekt.model.Group;
import com.bimmerlynge.andprojekt.model.User;
import com.bimmerlynge.andprojekt.persistence.EntryRepository;
import com.bimmerlynge.andprojekt.persistence.GroupRepository;
import com.bimmerlynge.andprojekt.persistence.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private Group currentGroup;
    private UserRepository userRepository;
    private GroupRepository groupRepository;
    private EntryRepository entryRepository;
    private Toolbar toolbar;
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

    public void setToolbar(Toolbar t){
        toolbar = t;
    }

    public LiveData<FirebaseUser> getCurrentUser(){
        return userRepository.getCurrentUser();
    }

    public void addNewEntry(String itemName, double itemPrice, String category){
        long todayLong = System.currentTimeMillis();
        Date date = new Date(todayLong);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(date);
        Entry newEntry = new Entry(itemName, itemPrice, category, dateString);
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



        Group toCreate = new Group(groupName, Double.parseDouble(budgetPrPerson), 202204);

        FirebaseUser currentUser = getCurrentUser().getValue();
        User creator = new User(currentUser.getUid(), currentUser.getDisplayName(), toCreate.getBudgetPerUser());
        toCreate.addMember(creator);
        groupRepository.createGroup(toCreate);

    }

    private boolean checkCreateGroupInput(String groupName, String budgetPrPerson){
        return !groupName.trim().isEmpty() && !budgetPrPerson.trim().isEmpty();
    }



    public LiveData<Group> getCurrentGroup(){

        return groupRepository.getCurrentGroup();}

    public void signOut(){userRepository.signOut();}



    public Group getGroup() {
        return currentGroup;
    }

    public LiveData<List<Entry>> getEntriesByUser() throws NullPointerException {
        //entryRepository.getEntries();

        return entryRepository.getThisMonthsEntries();
    }

    public LiveData<List<Entry>> getGroupEntriesThisMonth(){
        return entryRepository.getGroupEntriesThisMonth();
    }

    public LiveData<List<Entry>> getGroupEntriesLastMonth() {
        return entryRepository.getGroupEntriesLastMonth();
    }

    public void addUserToGroup(String id, String name) {
        User user = new User(id, name, currentGroup.getBudgetPerUser());
        currentGroup.addMember(user);
        groupRepository.updateGroupData(currentGroup);
    }

    public LiveData<Group> getGroupUpdates() {
        return groupRepository.getGroupUpdates();
    }

    public void newMonth() {
        currentGroup.newMonth();
        groupRepository.updateGroupData(currentGroup);
    }

    public LiveData<List<Entry>> getEntriesByGroup() {
        return entryRepository.getAllEntries();
    }

    public Toolbar getToolBar() {
        return toolbar;
    }

    public LiveData<List<Entry>> getGroupEntriesTwoAgo() {
        return entryRepository.getGroupEntriesTwoMonthsAgo();
    }


//    public LiveData<List<Entry>> listenForEntryChanges() {
//       return groupRepository.getGroupEntries();
//    }
}