package com.bimmerlynge.andprojekt.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bimmerlynge.andprojekt.R;
import com.bimmerlynge.andprojekt.databinding.FragmentHomeBinding;
import com.bimmerlynge.andprojekt.model.Group;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView rView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GroupViewModel groupViewModel =
                new ViewModelProvider(requireActivity()).get(GroupViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rView = root.findViewById(R.id.listView);
        rView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        rView.hasFixedSize();

        ArrayList<Group> list = groupViewModel.getAllGroups();

        GroupAdapter adapter = new GroupAdapter(list);
        rView.setAdapter( adapter);
        adapter.setOnGroupClickListener(group -> {
            groupViewModel.setCurrentGroup(group);
            Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_groupFragment);
        });

        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public interface ClickListener{
        void onClick(View view, int position);
    }


}