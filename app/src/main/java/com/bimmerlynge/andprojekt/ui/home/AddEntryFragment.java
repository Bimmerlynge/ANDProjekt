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

import com.bimmerlynge.andprojekt.R;
import com.bimmerlynge.andprojekt.model.Entry;
import com.google.android.material.snackbar.Snackbar;

public class AddEntryFragment extends Fragment {
    private Entry toAdd;
    private HomeViewModel viewModel;
    private EditText addItem;
    private EditText addPrice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_entry, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        Log.i("mig", "AddEntry gruppe: " + viewModel.getGroup().getName());
        addItem = rootView.findViewById(R.id.addentry_name);
        addPrice = rootView.findViewById(R.id.addentry_price);

        RadioGroup radioGroup = rootView.findViewById(R.id.addentry_radiogroup);
        Button addButton = rootView.findViewById(R.id.addentry_addbutton);
        addButton.setOnClickListener(view ->{
            if (!checkInput(addItem, addPrice)){
                Toast.makeText(getContext(), "Make sure to fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            int selectedId = radioGroup.getCheckedRadioButtonId();
            RadioButton selected = rootView.findViewById(selectedId);
            String itemName = addItem.getText().toString();
            double itemPrice = Double.parseDouble(addPrice.getText().toString());
            String category = selected.getText().toString();
            viewModel.addNewEntry(itemName, itemPrice, category);

            clearFields();
            Snackbar.make(getView(), "Succesfully added new entry", Snackbar.LENGTH_LONG).show();
        });


        return rootView;
    }
    private void clearFields(){
        addItem.setText("");
        addPrice.setText(null);
        addPrice.onEditorAction(EditorInfo.IME_ACTION_DONE);

    }



    private boolean checkInput(EditText item, EditText price){
            if (item.getText().toString().trim().isEmpty())
                return false;
            try {
                Double.parseDouble(price.getText().toString());
            } catch (NumberFormatException ignored){}
            return true;
    }
}
