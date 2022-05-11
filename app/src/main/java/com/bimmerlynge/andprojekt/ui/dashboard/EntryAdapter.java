package com.bimmerlynge.andprojekt.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bimmerlynge.andprojekt.R;
import com.bimmerlynge.andprojekt.model.Entry;
import com.bimmerlynge.andprojekt.ui.home.GroupAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder> {
    private List<Entry> entries;

    public EntryAdapter(){entries = new ArrayList<>();
    }
    public void setEntries(List<Entry> entries){
        this.entries = entries;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_entry_item, parent, false);

        return new EntryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemName.setText(entries.get(position).getItemName());
        holder.itemPrice.setText(entries.get(position).getItemPrice()+"");
        holder.itemCategory.setText(entries.get(position).getItemCategory());
        holder.date.setText(dateFormat(entries.get(position).getDate()));
    }
    private String dateFormat(long dateLong){
        Date date = new Date(dateLong);
        SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yy");
        return dFormat.format(date);
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemPrice, itemCategory, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.entryName);
            itemPrice = itemView.findViewById(R.id.entryPrice);
            itemCategory = itemView.findViewById(R.id.entryCategory);
            date = itemView.findViewById(R.id.entryDate);

        }
    }
}
