package com.bimmerlynge.andprojekt.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.bimmerlynge.andprojekt.R;
import com.bimmerlynge.andprojekt.model.Entry;
import com.google.android.material.snackbar.Snackbar;

public class AddEntryFragment extends Fragment {
    private Entry toAdd;
    private HomeViewModel viewModel;
    private EditText addItem;
    private EditText addPrice;
    private RadioGroup rg1, rg2;
    private boolean isChecking = true;
    private int checkedId = R.id.radioButton8;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_entry, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        addItem = rootView.findViewById(R.id.addentry_name);
        addPrice = rootView.findViewById(R.id.addentry_price);


        rg1 = rootView.findViewById(R.id.addentry_rg1);
        rg2 = rootView.findViewById(R.id.addentry_rg2);
        Button addButton = rootView.findViewById(R.id.addentry_addbutton);

        Log.i("mig", "checkedId start: " + checkedId);

        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i != -1 && isChecking) {

                    isChecking = false;
                    rg2.clearCheck();
                    checkedId = i;
                }
                isChecking = true;

            }
        });

        rg2.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i != -1 && isChecking){
                isChecking =false;
                rg1.clearCheck();
                checkedId =radioGroup.getCheckedRadioButtonId();
            }
            isChecking = true;

        }));

        addButton.setOnClickListener(view -> {
            if (!checkInput(addItem, addPrice)) {
                Toast.makeText(getContext(), "Make sure to fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                String itemName = addItem.getText().toString();
                double itemPrice = Double.parseDouble(addPrice.getText().toString());
                RadioButton checked = rootView.findViewById(checkedId);
                String category = checked.getTag().toString();
                viewModel.addNewEntry(itemName, itemPrice, category);

                clearFields();
                Snackbar.make(getView(), "Succesfully added new entry", Snackbar.LENGTH_LONG).show();
            }
        });





        return rootView;
    }

    private void clearFields() {
        addItem.setText("");
        addPrice.setText(null);
        addPrice.onEditorAction(EditorInfo.IME_ACTION_DONE);

    }


    private boolean checkInput(EditText item, EditText price) {
        if (item.getText().toString().trim().isEmpty())
            return false;
        try {
            Double.parseDouble(price.getText().toString());
        } catch (NumberFormatException ignored) {
        }
        return true;
    }
}
