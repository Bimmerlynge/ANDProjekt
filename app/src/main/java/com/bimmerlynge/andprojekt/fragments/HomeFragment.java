package com.bimmerlynge.andprojekt.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.bimmerlynge.andprojekt.R;
import com.bimmerlynge.andprojekt.databinding.FragmentHomeBinding;
import com.bimmerlynge.andprojekt.viewModels.SignInViewModel;
import com.bimmerlynge.andprojekt.viewModels.GroupViewModel;
import com.google.android.material.snackbar.Snackbar;

/*
 * A placeholder fragment for users who are not linked with a group
 */

public class HomeFragment extends Fragment {
    private GroupViewModel viewModel;
    //private SignInViewModel signInViewModel;
    private FragmentHomeBinding binding;
    private View root;

    private TextView welcomeLabel;
    private Button createGroup;

    //For popup window
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button createButton;
    private EditText groupName, budgetPrPerson;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        setupViews();
        init();

        return root;
    }

    private void init() {
        viewModel = new ViewModelProvider(requireActivity()).get(GroupViewModel.class);
        viewModel.init();

        checkForGroup();
        createGroup.setOnClickListener(view -> {
            createNewGroupDialog();
        });

    }

    private void setupViews() {
        welcomeLabel = root.findViewById(R.id.welcomeLabel);
        createGroup = root.findViewById(R.id.createGroup);
    }


    private void checkForGroup() {
        viewModel.getCurrentGroup().observe(getViewLifecycleOwner(), group -> {
            if (group != null) {
                viewModel.setCurrentGroup(group);
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_navigation_home_to_groupFragment);
            }
        });
    }


    private void createNewGroupDialog() {
        dialogBuilder = new AlertDialog.Builder(getContext());
        final View groupPopUp = getLayoutInflater().inflate(R.layout.fragment_create_group, null);
        groupName = groupPopUp.findViewById(R.id.create_groupName);
        budgetPrPerson = groupPopUp.findViewById(R.id.create_BudgetPerPerson);
        createButton = groupPopUp.findViewById(R.id.createGroup);

        dialogBuilder.setView(groupPopUp);
        dialog = dialogBuilder.create();
        dialog.show();

        createButton.setOnClickListener(view -> {
            String name = groupName.getText().toString();
            String budgetPrPersonString = budgetPrPerson.getText().toString();
            viewModel.CreateGroup(name, budgetPrPersonString);
            dialog.dismiss();
            Snackbar.make(getView(), "Created new group", Snackbar.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}