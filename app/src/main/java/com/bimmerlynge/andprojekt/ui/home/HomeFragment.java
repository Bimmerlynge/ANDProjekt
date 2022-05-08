package com.bimmerlynge.andprojekt.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bimmerlynge.andprojekt.R;
import com.bimmerlynge.andprojekt.SignInAcitivity;
import com.bimmerlynge.andprojekt.databinding.FragmentHomeBinding;
import com.bimmerlynge.andprojekt.model.Group;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView rView;
    private TextView welcomeLabel;
    private HomeViewModel viewModel;
    private Button createGroup;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        viewModel.init();
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        welcomeLabel = root.findViewById(R.id.welcomeLabel);
        checkIfSignedIn();
        createGroup = root.findViewById(R.id.createGroup);
        createGroup.setOnClickListener(view -> {
            Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_createGroupFragment);
        });

        rView = root.findViewById(R.id.listView);
        rView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        rView.hasFixedSize();

        //ArrayList<Group> list = homeViewModel.getAllGroups();

        //GroupAdapter adapter = new GroupAdapter(list);
//        rView.setAdapter(adapter);
//        adapter.setOnGroupClickListener(new GroupAdapter.OnGroupClickListener() {
//            @Override
//            public void onGroupClick(Group group) {
//                homeViewModel.setCurrentGroup(group);
//                Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_groupFragment);
//            }
//            @Override
//            public void onAddEntry(Group group) {
//                Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_addEntryFragment);
//
//            }
//        });

        return root;
    }

    private void checkIfSignedIn() {
        viewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                String message = "Welcome " + user.getDisplayName();
                welcomeLabel.setText(message);
            } else {
                startLoginActivity();
            }
        });
    }

    private void startLoginActivity() {
        startActivity(new Intent(getContext(), SignInAcitivity.class));
        getActivity().finish();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}