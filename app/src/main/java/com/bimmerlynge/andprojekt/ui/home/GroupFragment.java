package com.bimmerlynge.andprojekt.ui.home;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bimmerlynge.andprojekt.R;
import com.bimmerlynge.andprojekt.model.Group;

public class GroupFragment extends Fragment {

    HomeViewModel homeViewModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.group_fragment, container, false);



        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        Group toSet = homeViewModel.getCurrentGroup();
        TextView title = view.findViewById(R.id.groupTitle);
        TextView groupRemainder = view.findViewById(R.id.groupRemainder);
        RecyclerView listView = view.findViewById(R.id.member_list);
        listView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        listView.hasFixedSize();

        MemberAdapter adapter = new MemberAdapter(toSet.getMembers());
        listView.setAdapter(adapter);

        title.setText(toSet.getName());
        groupRemainder.setText(""+toSet.getRemain());



        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        homeViewModel.setCurrentGroup(null);
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == androidx.navigation.ui.R.id.home)
//            getActivity().onBackPressed();
//        return super.onOptionsItemSelected(item);
//    }
}