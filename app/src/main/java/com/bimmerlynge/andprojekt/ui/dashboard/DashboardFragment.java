package com.bimmerlynge.andprojekt.ui.dashboard;

import android.content.Intent;
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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bimmerlynge.andprojekt.R;
import com.bimmerlynge.andprojekt.SignInAcitivity;
import com.bimmerlynge.andprojekt.databinding.FragmentDashboardBinding;
import com.bimmerlynge.andprojekt.ui.home.HomeViewModel;

import org.w3c.dom.Text;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private HomeViewModel viewModel;
    private RecyclerView entries;
    private TextView title;
    private Button addEntry;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //checkIfHasGroup();
        entries = root.findViewById(R.id.rViewDashEntries);
        title = root.findViewById(R.id.welcomeLabel);
        addEntry = root.findViewById(R.id.dashBord_addEntry);

        addEntry.setOnClickListener(view -> {
            Navigation.findNavController(getView()).navigate(R.id.action_navigation_dashboard_to_addEntryFragment);
        });

        entries.setLayoutManager(new LinearLayoutManager(root.getContext()));
        entries.hasFixedSize();

        EntryAdapter adapter = new EntryAdapter();
        entries.setAdapter(adapter);
        try {
        viewModel.getEntriesByUser().observe(getViewLifecycleOwner(), entries -> {
            adapter.setEntries(entries);
        });
        } catch (NullPointerException e){
                Toast.makeText(getContext(), "No group detected", Toast.LENGTH_LONG).show();
                addEntry.setEnabled(false);
                title.setText("Must be part of a group to use this page");
        }





        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

//    private void checkIfHasGroup() {
//        if (viewModel.getGroup() == null)
//            startDashBoardNoGroup();
//    }

}