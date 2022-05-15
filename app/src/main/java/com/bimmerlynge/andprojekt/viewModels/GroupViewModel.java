package com.bimmerlynge.andprojekt.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bimmerlynge.andprojekt.model.Entry;
import com.bimmerlynge.andprojekt.model.Group;
import com.bimmerlynge.andprojekt.model.User;
import com.bimmerlynge.andprojekt.persistence.EntryRepository;
import com.bimmerlynge.andprojekt.persistence.GroupRepository;
import com.bimmerlynge.andprojekt.persistence.UserRepository;
import com.bimmerlynge.andprojekt.util.InputValidator;
import com.bimmerlynge.andprojekt.util.DateParser;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GroupViewModel extends AndroidViewModel {

    private Group currentGroup;
    private UserRepository userRepository;
    private GroupRepository groupRepository;
    private EntryRepository entryRepository;


    public GroupViewModel(Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
        groupRepository = GroupRepository.getInstance();
        entryRepository = EntryRepository.getInstance();


    }

    public void init() {
        String userId = userRepository.getCurrentUser().getValue().getUid();
        groupRepository.init(userId);
        entryRepository.init();

    }

    public void CreateGroup(String groupName, String budgetPrPerson) {
        if (!InputValidator.checkCreateGroupInput(groupName, budgetPrPerson))
            return;

        Group toCreate = new Group(groupName, Double.parseDouble(budgetPrPerson), DateParser.getYearMonthAsInt());
        FirebaseUser currentUser = userRepository.getCurrentUser().getValue();
        User creator = new User(currentUser.getUid(), currentUser.getDisplayName(), toCreate.getBudgetPerUser());
        toCreate.addMember(creator);
        groupRepository.createGroup(toCreate);
    }

    public void setCurrentGroup(Group group) {
        currentGroup = group;
        entryRepository.setGroup(group);
        groupRepository.setGroup(group);
    }

    public LiveData<Group> getCurrentGroup() {
        return groupRepository.getCurrentGroup();
    }

    public Group getGroup() {
        return currentGroup;
    }

    public void addUserToGroup(String id, String name) {
        User user = new User(id, name, currentGroup.getBudgetPerUser());
        currentGroup.addMember(user);
        groupRepository.updateGroupData(currentGroup);
    }

    public LiveData<Group> getGroupUpdates() {
        return groupRepository.getGroupUpdates();
    }

    public boolean newMonth() {
        return currentGroup.getYearMonth() < DateParser.getYearMonthAsInt();
    }

    public void updateNewMonth(){
        currentGroup.newMonth();
        groupRepository.updateGroupData(currentGroup);
    }

//    public LiveData<FirebaseUser> getCurrentUser() {
//        return userRepository.getCurrentUser();
//    }


    //    public LiveData<FirebaseUser> getCurrentUser(){
//        return userRepository.getCurrentUser();
//    }
    //TODO Metoder herunder skal flyttes
    public void addNewEntry(String itemName, double itemPrice, String category) {
        long todayLong = System.currentTimeMillis();
        Date date = new Date(todayLong);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(date);
        Entry newEntry = new Entry(itemName, itemPrice, category, dateString);
        entryRepository.addNewEntry(newEntry);
    }


    public LiveData<List<Entry>> getEntriesByUser() throws NullPointerException {
        //entryRepository.getEntries();

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


    public LiveData<List<Entry>> getGroupEntriesTwoAgo() {
        return entryRepository.getGroupEntriesTwoMonthsAgo();
    }
}