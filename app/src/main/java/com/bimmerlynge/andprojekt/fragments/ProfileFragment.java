package com.bimmerlynge.andprojekt.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bimmerlynge.andprojekt.R;
import com.bimmerlynge.andprojekt.databinding.FragmentProfileBinding;
import com.bimmerlynge.andprojekt.viewModels.ProfileViewModel;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;

    private EditText displayName, id;
    private Button saveChanges;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        displayName = root.findViewById(R.id.profile_displayName);
        displayName.setText(viewModel.getDisplayName());
        saveChanges = root.findViewById(R.id.profile_saveChanges);
        saveChanges.setOnClickListener(view -> {
            String name = displayName.getText().toString();
            viewModel.setDisplayName(name);
        });
        id = root.findViewById(R.id.profileId);
        id.setEnabled(false);
        id.setText(viewModel.getUserId());




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}