package com.bimmerlynge.andprojekt.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import com.bimmerlynge.andprojekt.R;
import com.bimmerlynge.andprojekt.viewModels.SignInViewModel;
import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;
import java.util.List;

public class SignInActivity extends AppCompatActivity {

    private SignInViewModel viewModel;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    goToMainActivity();
                }
                else
                    Toast.makeText(this, "SIGN IN CANCELLED", Toast.LENGTH_SHORT).show();
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SignInViewModel.class);
        checkIfSignedIn();
        setContentView(R.layout.signin_activity);
        // Automatically starting the log ing intent causes some issues
        //signIn();
    }
    private void goToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void checkIfSignedIn(){
        viewModel.getCurrentUser().observe(this, user -> {
            if (user != null) {
                goToMainActivity();
            }
        });
    }


    public void signIn(View view) {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();

        activityResultLauncher.launch(signInIntent);
    }
}
