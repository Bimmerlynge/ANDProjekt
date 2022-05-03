package com.bimmerlynge.andprojekt.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Log.i("Mig", "I onViewCreated");
        rView = root.findViewById(R.id.listView);
        rView.setLayoutManager(new LinearLayoutManager(root.getContext()));
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

            }
        };

        GroupAdapter adapter = new GroupAdapter(list);
        rView.setAdapter( adapter);


        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}