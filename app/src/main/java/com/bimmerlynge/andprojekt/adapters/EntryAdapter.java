package com.bimmerlynge.andprojekt.adapters;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bimmerlynge.andprojekt.R;
import com.bimmerlynge.andprojekt.fragments.DashboardFragment;
import com.bimmerlynge.andprojekt.model.Entry;
import com.bimmerlynge.andprojekt.util.Parser;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class    EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder> {
    private Context context;
    private List<Entry> entries;
    private View root;

    public EntryAdapter(Context context){
        this.context = context;
        entries = new ArrayList<>();
    }
    public void setEntries(List<Entry> entries){
        this.entries = entries;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        root = inflater.inflate(R.layout.single_entry_item, parent, false);

        return new EntryAdapter.ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Entry item = entries.get(position);
        holder.itemName.setText(entries.get(position).getItemName());
        holder.itemPrice.setText(entries.get(position).getItemPrice()+"");
        holder.itemCategory.setText(Parser.categoryToProperResource(root.getContext(),entries.get(position).getItemCategory()));
        holder.date.setText(Parser.yearMonthDayToDayMonthYear(entries.get(position).getDate()));

    }

    @Override
    public int getItemCount() {
        return entries.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout foreground, background;
        TextView itemName, itemPrice, itemCategory, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            foreground = itemView.findViewById(R.id.foreground);
            background = itemView.findViewById(R.id.background);
            itemName = itemView.findViewById(R.id.entryName);
            itemPrice = itemView.findViewById(R.id.entryPrice);
            itemCategory = itemView.findViewById(R.id.entryCategory);
            date = itemView.findViewById(R.id.entryDate);
        }

    }

    public void removeItem(int position){

    }

}
