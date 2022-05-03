package com.bimmerlynge.andprojekt.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bimmerlynge.andprojekt.R;
import com.bimmerlynge.andprojekt.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView rView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("Mig", "I onViewCreated");
        rView = view.findViewById(R.id.listView);
        rView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rView.hasFixedSize();

        ArrayList<Group> list = new ArrayList(){
            {
                add(new Group("Gruppe 1", 115));
                add(new Group("Gruppe 2", 142));
                add(new Group("Gruppe 3", 1154));
                add(new Group("Gruppe 4", 675));
                add(new Group("Gruppe 5", 532));
                add(new Group("Gruppe 1", 115));
                add(new Group("Gruppe 2", 142));
                add(new Group("Gruppe 3", 1154));
                add(new Group("Gruppe 4", 675));
                add(new Group("Gruppe 5", 532));
                add(new Group("Gruppe 1", 115));
                add(new Group("Gruppe 2", 142));
                add(new Group("Gruppe 3", 1154));
                add(new Group("Gruppe 4", 675));
                add(new Group("Gruppe 5", 532));
            }
        };

        GroupAdapter adapter = new GroupAdapter(list);
        rView.setAdapter( adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}