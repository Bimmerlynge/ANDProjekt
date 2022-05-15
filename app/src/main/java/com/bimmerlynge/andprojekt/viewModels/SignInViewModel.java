package com.bimmerlynge.andprojekt.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bimmerlynge.andprojekt.persistence.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.Random;

public class SignInViewModel extends AndroidViewModel {

    private int test;
    private UserRepository userRepository;
    public SignInViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance(application);
        Random rand = new Random();
        test = rand.nextInt(100);
        Log.i("mig", "laver den nu vm?" + test);
    }


    public LiveData<FirebaseUser> getCurrentUser(){
        return userRepository.getCurrentUser();
    }

    public void signOut(){userRepository.signOut();}
}
