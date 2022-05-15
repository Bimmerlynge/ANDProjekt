package com.bimmerlynge.andprojekt.viewModels;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bimmerlynge.andprojekt.persistence.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ProfileViewModel extends AndroidViewModel {

    private LiveData<FirebaseUser> mUser;
    private UserRepository userRepository;

    public ProfileViewModel(Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
        mUser = userRepository.getCurrentUser();


    }

    public String getDisplayName(){
        return mUser.getValue().getDisplayName();
    }
    public String getUserId(){return mUser.getValue().getUid();}

    public void setDisplayName(String name){
        UserProfileChangeRequest updates = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
        mUser.getValue().updateProfile(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete())
                    Toast.makeText(getApplication().getApplicationContext(), "Updated Display Name", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplication().getApplicationContext(), "Error updating...", Toast.LENGTH_SHORT).show();
            }
        });
    }

}