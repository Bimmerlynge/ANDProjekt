package com.bimmerlynge.andprojekt.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bimmerlynge.andprojekt.R;
import com.bimmerlynge.andprojekt.viewModels.GroupViewModel;
import com.bimmerlynge.andprojekt.adapters.MemberAdapter;
import com.google.android.material.snackbar.Snackbar;

public class GroupFragment extends Fragment {
    //Popup window
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText userId, userName;
    private Button addMember;

    //Views
    private TextView title, groupRemainder;
    private Button startNewMonth, addNew, statsButton;
    private RecyclerView listView;


    private View root;
    private GroupViewModel groupViewModel;
    private MemberAdapter adapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.group_fragment, container, false);
        setupViews();
        init();
        return root;
    }

    private void init(){
        groupViewModel = new ViewModelProvider(requireActivity()).get(GroupViewModel.class);
        title.setText(groupViewModel.getGroup().getName());
        setupRecycleView();
        setRViewData();
        newMonthCheck();
        setupStatsButton();
        setupPopup();
    }

    private void setupViews() {
        title = root.findViewById(R.id.groupTitle);
        startNewMonth = root.findViewById(R.id.startnewmonth);
        groupRemainder = root.findViewById(R.id.groupRemainder);
        listView = root.findViewById(R.id.member_list);
        addNew = root.findViewById(R.id.add_member);
        statsButton = root.findViewById(R.id.showStats);
    }

    private void setupStatsButton() {
        statsButton.setOnClickListener(v -> {
            Navigation.findNavController(getView()).navigate(R.id.action_groupFragment_to_statsFragment);
        });
    }

    private void setRViewData() {
        groupViewModel.getGroupUpdates().observe(getViewLifecycleOwner(), group -> {
            adapter.setMembers(group);
            groupRemainder.setText(group.getRemain()+"");
        });
    }

    private void newMonthCheck(){
        if (groupViewModel.newMonth()) {
            startNewMonth.setEnabled(true);
            startNewMonth.setOnClickListener(v -> {
                groupViewModel.updateNewMonth();
                startNewMonth.setEnabled(false);
            });
        }
    }

    private void setupRecycleView(){
        listView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        listView.hasFixedSize();
        adapter = new MemberAdapter();
        listView.setAdapter(adapter);
    }

    private void setupPopup() {
        addNew.setOnClickListener(v -> {
            createAddUserDialog();
        });
    }

    private void createAddUserDialog(){
        dialogBuilder = new AlertDialog.Builder(getContext());
        final View userPopUp = getLayoutInflater().inflate(R.layout.add_member, null);
        userId = userPopUp.findViewById(R.id.add_member_id);
        addMember = userPopUp.findViewById(R.id.add_member_add);
        userName = userPopUp.findViewById(R.id.add_member_name);
        dialogBuilder.setView(userPopUp);
        dialog = dialogBuilder.create();
        dialog.show();


        addMember.setOnClickListener(view -> {
            String id = userId.getText().toString();
            String name = userName.getText().toString();
            groupViewModel.addUserToGroup(id, name);
            dialog.dismiss();
            Snackbar.make(getView(), "Added user to group", Snackbar.LENGTH_SHORT).show();
        });

    }
}