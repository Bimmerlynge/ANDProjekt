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
import com.bimmerlynge.andprojekt.viewModels.SignInViewModel;

public class ProfileFragment extends Fragment {
    private View root;
    private FragmentProfileBinding binding;
    private SignInViewModel viewModel;

    private EditText displayName, id;
    private Button saveChanges;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        setupViews();
        init();

        return root;
    }
    private void init(){
        viewModel = new ViewModelProvider(requireActivity()).get(SignInViewModel.class);
        displayName.setText(viewModel.getDisplayName());
        id.setEnabled(false);
        id.setText(viewModel.getUserId());
        createSaveChangesButton();
    }
    private void setupViews(){
        displayName = root.findViewById(R.id.profile_displayName);
        saveChanges = root.findViewById(R.id.profile_saveChanges);
        id = root.findViewById(R.id.profileId);
    }
    private void createSaveChangesButton(){
        saveChanges.setOnClickListener(view -> {
            String name = displayName.getText().toString();
            viewModel.setDisplayName(name);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}