package com.bimmerlynge.andprojekt.persistence;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;

public class SignInViewModel extends AndroidViewModel {


    private UserRepository userRepository;
    public SignInViewModel(@NonNull Application application) {

        super(application);
        userRepository = UserRepository.getInstance(application);
    }


    public LiveData<FirebaseUser> getCurrentUser(){
        return userRepository.getCurrentUser();
    }
}
