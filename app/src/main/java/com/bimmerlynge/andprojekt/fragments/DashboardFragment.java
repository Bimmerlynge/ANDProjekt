package com.bimmerlynge.andprojekt.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bimmerlynge.andprojekt.R;

import com.bimmerlynge.andprojekt.databinding.FragmentDashboardBinding;
import com.bimmerlynge.andprojekt.adapters.EntryAdapter;
import com.bimmerlynge.andprojekt.util.RecyclerItemTouchHelper;
import com.bimmerlynge.andprojekt.viewModels.EntryViewModel;
import com.bimmerlynge.andprojekt.viewModels.GroupViewModel;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

public class DashboardFragment extends Fragment {
    private View root;
    private FragmentDashboardBinding binding;
    private GroupViewModel viewModel;
    private EntryViewModel entryViewModel;
    private SwipeableRecyclerView entries;
    private TextView title;
    private Button addEntry;
    private EntryAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        setupViews();
        init();



        return root;
    }

    private void init() {
        viewModel = new ViewModelProvider(requireActivity()).get(GroupViewModel.class);
        entryViewModel = new ViewModelProvider(requireActivity()).get(EntryViewModel.class);
        checkIfHasGroup();
        setupRecyclerView();
        setupAddEntryButton();
    }



    private void setupViews() {
        entries = root.findViewById(R.id.rViewDashEntries);
        title = root.findViewById(R.id.welcomeLabel);
        addEntry = root.findViewById(R.id.dashBord_addEntry);
    }

    private void setupRecyclerView() {
        entries.setLayoutManager(new LinearLayoutManager(root.getContext()));
        entries.hasFixedSize();
        entries.setItemAnimator(new DefaultItemAnimator());
        adapter = new EntryAdapter(getContext());

        entries.setAdapter(adapter);
        entries.setListener(new SwipeLeftRightCallback.Listener() {
            @Override
            public void onSwipedLeft(int position) {
                Log.i("mig", "Swiped left");
            }

            @Override
            public void onSwipedRight(int position) {
                Log.i("mig", "Swiped right");
            }
        });


    }

    private void setupAddEntryButton() {
        addEntry.setOnClickListener(view -> {
            NavHostFragment.findNavController(DashboardFragment.this).navigate(R.id.action_navigation_dashboard_to_addEntryFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void checkIfHasGroup() {
        if (viewModel.getGroup() == null) {
            addEntry.setEnabled(false);
            viewModel.getCurrentGroup().observe(getViewLifecycleOwner(), group -> {
                viewModel.setCurrentGroup(group);
                addEntry.setEnabled(false);
            });
            return;
        }
        entryViewModel.getEntriesByUser().observe(getViewLifecycleOwner(), list -> {
            adapter.setEntries(list);
        });
    }

}