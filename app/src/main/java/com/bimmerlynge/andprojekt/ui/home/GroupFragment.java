package com.bimmerlynge.andprojekt.ui.home;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bimmerlynge.andprojekt.R;
import com.bimmerlynge.andprojekt.model.Group;
import com.google.android.material.snackbar.Snackbar;

public class GroupFragment extends Fragment {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText userId, userName;
    private Button addMember;

    HomeViewModel homeViewModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.group_fragment, container, false);



        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        Group toSet = homeViewModel.getGroup();
        TextView title = view.findViewById(R.id.groupTitle);
        TextView groupRemainder = view.findViewById(R.id.groupRemainder);
        RecyclerView listView = view.findViewById(R.id.member_list);
        Button addNew = view.findViewById(R.id.add_member);
        addNew.setOnClickListener(v -> {
            createAddUserDialog();
        });
        listView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        listView.hasFixedSize();

        MemberAdapter adapter = new MemberAdapter();
        listView.setAdapter(adapter);

        homeViewModel.getGroupUpdates().observe(getViewLifecycleOwner(), group -> {
            adapter.setMembers(group.getMembers());
            groupRemainder.setText(group.getRemain()+"");
        });

//        homeViewModel.listenForEntryChanges().observe(getViewLifecycleOwner(), entries -> {
//            Log.i("mig", "GroupFragment heard new entries");
//        });

        title.setText(toSet.getName());




        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void createAddUserDialog(){
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
            homeViewModel.addUserToGroup(id, name);
            dialog.dismiss();
            Snackbar.make(getView(), "Added user to group", Snackbar.LENGTH_LONG).show();
        });

    }
}