package com.bimmerlynge.andprojekt.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bimmerlynge.andprojekt.model.Group;
import com.bimmerlynge.andprojekt.model.User;
import com.bimmerlynge.andprojekt.persistence.EntryRepository;
import com.bimmerlynge.andprojekt.persistence.GroupRepository;
import com.bimmerlynge.andprojekt.persistence.UserRepository;
import com.bimmerlynge.andprojekt.util.InputValidator;
import com.bimmerlynge.andprojekt.util.Parser;
import com.google.firebase.auth.FirebaseUser;

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
        try {
            String userId = userRepository.getCurrentUser().getValue().getUid();
            groupRepository.init(userId);
            entryRepository.init();
        } catch (NullPointerException ignored){}



    }

    public void CreateGroup(String groupName, String budgetPrPerson) {
        if (!InputValidator.checkCreateGroupInput(groupName, budgetPrPerson))
            return;

        Group toCreate = new Group(groupName, Double.parseDouble(budgetPrPerson), Parser.getYearMonthAsInt());
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
        return currentGroup.getYearMonth() < Parser.getYearMonthAsInt();
    }

    public void updateNewMonth(){
        currentGroup.newMonth();
        groupRepository.updateGroupData(currentGroup);
    }
}