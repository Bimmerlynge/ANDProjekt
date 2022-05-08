package com.bimmerlynge.andprojekt.ui.home;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bimmerlynge.andprojekt.R;
import com.bimmerlynge.andprojekt.model.Group;

import java.util.ArrayList;


public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    private ArrayList<Group> groups;
    private OnGroupClickListener listener;

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
        holder.remain.setText(""+ groups.get(position).getRemain());
    }

    public void setOnGroupClickListener(OnGroupClickListener listener){
        this.listener = listener;
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
            itemView.setTag(this);
            name = itemView.findViewById(R.id.group_name);
            remain = itemView.findViewById(R.id.total_remain);
            addEntry = itemView.findViewById(R.id.addEntry);

            name.setOnClickListener(v->{
                listener.onGroupClick(groups.get(getAdapterPosition()));
            });
            addEntry.setOnClickListener(v-> {
                listener.onAddEntry(groups.get(getAdapterPosition()));
            });
        }
    }
    public interface OnGroupClickListener{
        void onGroupClick(Group group);
        void onAddEntry(Group group);
    }


}
