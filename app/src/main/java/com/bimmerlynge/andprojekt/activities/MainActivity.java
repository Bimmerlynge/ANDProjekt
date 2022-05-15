package com.bimmerlynge.andprojekt.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.bimmerlynge.andprojekt.R;
import com.bimmerlynge.andprojekt.viewModels.SignInViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bimmerlynge.andprojekt.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private SignInViewModel signInViewModel;

    private NavController navController;
    AppBarConfiguration appBarConfiguration;
    BottomNavigationView navView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        signInViewModel = new ViewModelProvider(this).get(SignInViewModel.class);

        init();
        setupNavigation();
    }

    private void init(){
        navView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.materialToolbar);
        checkIfSignedIn();

    }

    private void setupNavigation(){
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());


        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        setToolBarVisibility();
    }

    private void setToolBarVisibility(){
        navController.addOnDestinationChangedListener(((controller, destination, arguments) -> {
            final int id = destination.getId();
            if (id == R.id.groupFragment)
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.logout) {
            signInViewModel.signOut();
            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkIfSignedIn() {
        signInViewModel.getCurrentUser().observe(this, user -> {
            if (user == null)
                startLoginActivity();
        });
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, SignInActivity.class));
        finish();

    }
}