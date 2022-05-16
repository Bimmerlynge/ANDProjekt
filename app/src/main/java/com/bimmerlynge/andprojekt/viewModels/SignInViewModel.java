package com.bimmerlynge.andprojekt.viewModels;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bimmerlynge.andprojekt.model.Group;
import com.bimmerlynge.andprojekt.model.User;
import com.bimmerlynge.andprojekt.persistence.GroupRepository;
import com.bimmerlynge.andprojekt.persistence.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Random;

public class SignInViewModel extends AndroidViewModel {


    private UserRepository userRepository;
    private GroupRepository groupRepository;
    private LiveData<FirebaseUser> mUser;

    public SignInViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance(application);
        groupRepository = GroupRepository.getInstance();
        mUser = userRepository.getCurrentUser();
    }


    public LiveData<FirebaseUser> getCurrentUser(){
        return userRepository.getCurrentUser();
    }

    public void signOut(){userRepository.signOut();}

    public String getDisplayName(){
        return mUser.getValue().getDisplayName();
    }
    public String getUserId(){return mUser.getValue().getUid();}

    public void setDisplayName(String name){
        UserProfileChangeRequest updates = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
        mUser.getValue().updateProfile(updates).addOnCompleteListener(task -> {
            if (task.isComplete()) {
                Toast.makeText(getApplication().getApplicationContext(), "Updated Display Name", Toast.LENGTH_SHORT).show();
                updateMemberName(name);
            }
            else
                Toast.makeText(getApplication().getApplicationContext(), "Error updating...", Toast.LENGTH_SHORT).show();
        });
    }
    private void updateMemberName(String name){
        Group group = groupRepository.getGroup();
        for (User member : group.getMembers()) {
            if (member.getId().equals(mUser.getValue().getUid()))
                member.setName(name);
        }
        groupRepository.updateGroupData(group);
    }
}
