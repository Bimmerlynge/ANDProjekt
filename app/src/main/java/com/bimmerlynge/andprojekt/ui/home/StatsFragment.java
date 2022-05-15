package com.bimmerlynge.andprojekt.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bimmerlynge.andprojekt.R;
import com.bimmerlynge.andprojekt.model.Entry;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class StatsFragment extends Fragment {
    HomeViewModel viewModel;
    SeekBar seekBar;
    TextView title;
    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList<BarEntry> barEntries;
    List<String> labels;
    //private final int[] colors = {R.color.meat, R.color.veggies, R.color.fruit, R.color.deli, R.color.grains, R.color.dairy, R.color.frozen, R.color.other};
    //private final int[] colors = {Color.RED, Color.GREEN, Color, R.color.deli, R.color.grains, R.color.dairy, R.color.frozen, R.color.other};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.piechart_fragment, container, false);
        viewModel =  new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        viewModel.init();

        title = view.findViewById(R.id.barchart_title);
        title.setText(getString(R.string.current_month));
        seekBar = view.findViewById(R.id.seekbar);
        labels = Arrays.asList(getString(R.string.meat)
                , getString(R.string.veggies)
                ,getString(R.string.fruit)
                ,getString(R.string.deli)
                ,getString(R.string.grains), getString(R.string.dairy), getString(R.string.frozen), getString(R.string.other));
        barChart = view.findViewById(R.id.barChart);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.setScaleEnabled(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setTextSize(11f);

        init();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                int progress = seekBar.getProgress();
                if (progress == 3) {
                    title.setText(R.string.current_month);
                    viewModel.getGroupEntriesThisMonth().observe(getViewLifecycleOwner(), l -> {
                        doStuff(l);
                    });
                }
                else if (progress ==2 ) {
                    title.setText(R.string.last_month);
                    viewModel.getGroupEntriesLastMonth().observe(getViewLifecycleOwner(), l -> {
                        doStuff(l);
                    });
                }
                else if (progress == 1) {
                    String twoMonthsAgo = getTwoMonthsAgo();
                    title.setText(twoMonthsAgo);
                    viewModel.getGroupEntriesTwoAgo().observe(getViewLifecycleOwner(), l -> {
                        doStuff(l);
                    });
                }
                else {
                    title.setText(R.string.all_time);
                    viewModel.getEntriesByGroup().observe(getViewLifecycleOwner(), l -> {
                        doStuff(l);
                    });
                }

            }
        });

        return view;
    }

    private String getTwoMonthsAgo(){
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.MONTH,-2);
        Date twoAgo = c.getTime();
        SimpleDateFormat format = new SimpleDateFormat("MMMM");
        return format.format(twoAgo);
    }

    private void init(){
        viewModel.getGroupEntriesThisMonth().observe(getViewLifecycleOwner(), list -> {
            doStuff(list);
        });
    }

    private void doStuff(List<Entry> list){
        List<Double> data = formatList(list);
        getBarEntries(data);
        barDataSet = new BarDataSet(barEntries, "");
        barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setValueTextSize(16f);

        barChart.getDescription().setEnabled(false);
        barDataSet.notifyDataSetChanged();
        barData.notifyDataChanged();

        barChart.notifyDataSetChanged();
        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.invalidate();
    }

    private List<Double> formatList(List<Entry> list){
        List<Double> data = new ArrayList<>();
        double meat =0, veggies = 0, fruit =0, deli = 0, grains = 0, dairy = 0,frozen =0,other =0;
        for (Entry entry : list) {
            String caseSubject = entry.getItemCategory();
            switch (caseSubject){
                case "Meat":
                    meat += entry.getItemPrice();
                    break;
                case "Veggies":
                    veggies += entry.getItemPrice();
                    break;
                case "Fruit":
                    fruit += entry.getItemPrice();
                    break;
                case "Deli":
                    deli += entry.getItemPrice();
                    break;
                case "Grains":
                    grains += entry.getItemPrice();
                    break;
                case "Dairy":
                    dairy += entry.getItemPrice();
                    break;
                case  "Frozen":
                    frozen += entry.getItemPrice();
                    break;
                case "Other":
                    other += entry.getItemPrice();
                    break;
                default:
            }
        }
        data.add(meat); data.add(veggies); data.add(fruit);data.add(deli);data.add(grains);data.add(dairy);data.add(frozen);data.add(other);

        return data;
    }


    private void getBarEntries(List<Double> list){
        barEntries = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            double item = list.get(i);
            barEntries.add(new BarEntry((float) i, (float) item));
        }
    }


}
