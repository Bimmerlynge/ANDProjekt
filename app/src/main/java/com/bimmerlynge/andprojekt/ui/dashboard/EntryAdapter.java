package com.bimmerlynge.andprojekt.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bimmerlynge.andprojekt.R;
import com.bimmerlynge.andprojekt.model.Entry;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder> {
    private List<Entry> entries;
    private Context context;

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
        context = view.getContext();
        return new EntryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemName.setText(entries.get(position).getItemName());
        holder.itemPrice.setText(entries.get(position).getItemPrice()+"");
        holder.itemCategory.setText(categoryFormatter(entries.get(position).getItemCategory()));
        holder.date.setText(dateFormat(entries.get(position).getDate()));
    }

    private String categoryFormatter(String category){
        String formatted = "";
        switch (category){
            case "Meat":
                formatted = context.getString(R.string.meat);
                break;
            case "Veggies":
                formatted = context.getString(R.string.veggies);
                break;
            case "Fruit":
                formatted = context.getString(R.string.fruit);
                break;
            case "Deli":
                formatted = context.getString(R.string.deli);
                break;
            case "Grains":
                formatted = context.getString(R.string.grains);
                break;
            case "Dairy":
                formatted = context.getString(R.string.dairy);
                break;
            case "Frozen":
                formatted = context.getString(R.string.frozen);
                break;
            case "Other":
                formatted = context.getString(R.string.other);
                break;
            default:
        }
        return formatted;
    }


    private String dateFormat(String date){
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2= new SimpleDateFormat("dd-MM-yy");
        String toReturn = "";
        try {
            Date d = format1.parse(date);
            toReturn = format2.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return toReturn;
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
