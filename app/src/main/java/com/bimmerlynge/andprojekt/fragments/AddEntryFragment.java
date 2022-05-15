package com.bimmerlynge.andprojekt.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bimmerlynge.andprojekt.R;
import com.bimmerlynge.andprojekt.model.Entry;
import com.bimmerlynge.andprojekt.util.InputValidator;
import com.bimmerlynge.andprojekt.viewModels.EntryViewModel;
import com.bimmerlynge.andprojekt.viewModels.GroupViewModel;
import com.google.android.material.snackbar.Snackbar;

public class AddEntryFragment extends Fragment {
    private View root;
    private Button addButton;
    private Entry toAdd;
    private GroupViewModel viewModel;
    private EditText addItem;
    private EditText addPrice;
    private RadioGroup rg1, rg2;
    private boolean isChecking = true;
    private int checkedId = R.id.radioButton8;
    private EntryViewModel entryViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.add_entry, container, false);
        setupViews();
        init();
        return root;
    }

    private void init(){
        viewModel = new ViewModelProvider(requireActivity()).get(GroupViewModel.class);
        entryViewModel = new ViewModelProvider(requireActivity()).get(EntryViewModel.class);
        setUpRadioGroups();
        setupAddEntryButton();
    }

    private void setupViews(){
        addItem = root.findViewById(R.id.addentry_name);
        addPrice = root.findViewById(R.id.addentry_price);
        rg1 = root.findViewById(R.id.addentry_rg1);
        rg2 = root.findViewById(R.id.addentry_rg2);
        addButton = root.findViewById(R.id.addentry_addbutton);
    }

    private void setUpRadioGroups(){
        rg1.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i != -1 && isChecking) {
                isChecking = false;
                rg2.clearCheck();
                checkedId = i;
            }
            isChecking = true;
        });

        rg2.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i != -1 && isChecking){
                isChecking =false;
                rg1.clearCheck();
                checkedId =radioGroup.getCheckedRadioButtonId();
            }
            isChecking = true;

        }));
    }

    private void setupAddEntryButton() {
        addButton.setOnClickListener(view -> {
            String itemName = addItem.getText().toString();
            String itemPrice = addPrice.getText().toString();
            RadioButton checked = root.findViewById(checkedId);
            String category = checked.getTag().toString();
            try {
                entryViewModel.addNewEntry(itemName, itemPrice, category);
                clearFields();
                Toast.makeText(getContext(), "Succesfully added new entry", Toast.LENGTH_SHORT).show();
            } catch (IllegalArgumentException e){
                Toast.makeText(getContext(), "Make sure to fill in proper values", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void clearFields() {
        addItem.setText("");
        addPrice.setText(null);
        addPrice.onEditorAction(EditorInfo.IME_ACTION_DONE);

    }



}
