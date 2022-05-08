package com.bimmerlynge.andprojekt.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bimmerlynge.andprojekt.R;


public class CreateGroupFragment extends Fragment {

    private EditText groupName;
    private EditText budgetPrPerson;
    private Button createGroup;
    private HomeViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_create_group, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        groupName = root.findViewById(R.id.create_groupName);
        budgetPrPerson = root.findViewById(R.id.create_BudgetPerPerson);
        createGroup = root.findViewById(R.id.createGroup);

        createGroup.setOnClickListener(view -> {
            createGroup();
        });


        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void createGroup(){
        viewModel.CreateGroup(groupName.getText().toString(), budgetPrPerson.getText().toString());
    }
}