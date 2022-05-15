package com.bimmerlynge.andprojekt.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bimmerlynge.andprojekt.R;
import com.bimmerlynge.andprojekt.model.Group;
import com.bimmerlynge.andprojekt.model.User;

import java.util.ArrayList;
import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {
    private List<User> members;
    private Group currentGroup;

    public MemberAdapter() { members = new ArrayList<>(); currentGroup = null;
    }

    public void setMembers(Group group){
        members = group.getMembers();
        currentGroup = group;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_user_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberAdapter.ViewHolder holder, int position) {
        holder.name.setText(members.get(position).getName()+":");
        holder.remain.setImageResource(checkVolume(members.get(position).getRemain()));
        holder.numRemain.setText(members.get(position).getRemain()+"");
    }

    private int checkVolume(double remain) {
        double max = currentGroup.getBudgetPerUser();
        double r = remain/max;
        if (remain == max)
            return R.drawable.ic_budget_full;
        else if (r < 1 && r >= 0.75)
            return R.drawable.ic_budget_partial_full;
        else if (r < 0.75 && r > 0.5)
            return R.drawable.ic_budget_half;
        else if (r < 0.5 && r > 0.25)
            return R.drawable.ic_budget_near_empty;
        else if (r < 0.25 && r > 0)
            return R.drawable.ic_budget_danger_close;
        else
            return R.drawable.ic_budget_empty;
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView remain;
        TextView numRemain;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_of_user);
            remain = itemView.findViewById(R.id.user_group_remain);
            numRemain = itemView.findViewById(R.id.user_numeric_remain);
        }
    }
}
