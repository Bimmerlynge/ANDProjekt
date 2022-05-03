package com.bimmerlynge.andprojekt.ui.home;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.bimmerlynge.andprojekt.R;
import com.bimmerlynge.andprojekt.model.Group;

import java.util.ArrayList;


public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    ArrayList<Group> groups;

    public GroupAdapter(ArrayList<Group> groups){
        this.groups = groups;
    }

    @NonNull
    @Override
    public GroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.group_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupAdapter.ViewHolder holder, int position) {
        holder.name.setText(groups.get(position).getName());
        holder.remain.setText(Double.toString(groups.get(position).getRemain()));
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView remain;
        Button addEntry;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.group_name);
            remain = itemView.findViewById(R.id.total_remain);
            addEntry = itemView.findViewById(R.id.addEntry);
            addEntry.setOnClickListener(view -> {
                Toast.makeText(itemView.getContext(), "You pressed on: " + name.getText(), Toast.LENGTH_LONG).show();
            });
            name.setOnClickListener(view -> {
                Toast.makeText(itemView.getContext(), name.getText() + " has a total remain of: " + remain.getText(), Toast.LENGTH_LONG).show();
            });
        }
    }

}
