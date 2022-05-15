package com.bimmerlynge.andprojekt.ui.home;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class GroupFragment extends Fragment {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText userId, userName;
    private TextView title;
    private Button addMember, startNewMonth, statsButton;
    private Group currentGroup;

    HomeViewModel homeViewModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.group_fragment, container, false);



        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        currentGroup = homeViewModel.getGroup();
        title = view.findViewById(R.id.groupTitle);
        startNewMonth = view.findViewById(R.id.startnewmonth);
        TextView groupRemainder = view.findViewById(R.id.groupRemainder);
        RecyclerView listView = view.findViewById(R.id.member_list);
        Button addNew = view.findViewById(R.id.add_member);
        statsButton = view.findViewById(R.id.showStats);

        if (newMonth()){
            startNewMonth.setEnabled(true);
            startNewMonth.setOnClickListener(v -> {
                homeViewModel.newMonth();
                startNewMonth.setEnabled(false);
            });
        }

        addNew.setOnClickListener(v -> {
            createAddUserDialog();
        });
        listView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        listView.hasFixedSize();

        MemberAdapter adapter = new MemberAdapter();
        listView.setAdapter(adapter);


        homeViewModel.getGroupUpdates().observe(getViewLifecycleOwner(), group -> {

            adapter.setMembers(group);
            groupRemainder.setText(group.getRemain()+"");
        });


        title.setText(currentGroup.getName());

        statsButton.setOnClickListener(v -> {
            Navigation.findNavController(getView()).navigate(R.id.action_groupFragment_to_statsFragment);
        });



        return view;
    }
    private boolean newMonth(){
        long today = System.currentTimeMillis();
        Date date = new Date(today);
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMM");
        String string = dFormat.format(date);
        int toInt = Integer.parseInt(string);

        return currentGroup.getYearMonth() < toInt;
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
            Snackbar.make(getView(), "Added user to group", Snackbar.LENGTH_SHORT).show();
        });

    }
}