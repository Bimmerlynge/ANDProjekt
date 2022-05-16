package com.bimmerlynge.andprojekt.fragments;

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
import com.bimmerlynge.andprojekt.util.Parser;
import com.bimmerlynge.andprojekt.viewModels.EntryViewModel;
import com.bimmerlynge.andprojekt.viewModels.GroupViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatsFragment extends Fragment {
    private View root;
    private EntryViewModel entryViewModel;
    GroupViewModel viewModel;
    SeekBar seekBar;
    TextView title;
    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    private ArrayList<BarEntry> barEntries;
    private List<String> labels;
    private final int[] colors = {Color.rgb(255, 50, 50), Color.rgb(60, 245, 60), Color.rgb(125, 125, 250)
                                    , Color.rgb(255, 207, 229), Color.rgb(255, 255, 50), Color.rgb(250, 250, 250)
                                    , Color.rgb(140, 157, 207)};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.piechart_fragment, container, false);

        setupViews();
        init();

        return root;
    }

    private void init(){
        entryViewModel =  new ViewModelProvider(requireActivity()).get(EntryViewModel.class);

        title.setText(getString(R.string.current_month));
        labels = Arrays.asList(getString(R.string.meat), getString(R.string.veggies) ,getString(R.string.fruit),getString(R.string.deli)
                ,getString(R.string.grains), getString(R.string.dairy), getString(R.string.frozen), getString(R.string.other));
        setupSeekBar();
        initBarChart();
        initDataLoad();

    }
    private void setupViews(){
        title = root.findViewById(R.id.barchart_title);
        seekBar = root.findViewById(R.id.seekbar);
        barChart = root.findViewById(R.id.barChart);
    }

    private void initBarChart(){
        barChart.setScaleEnabled(false);
        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setTextColor(Color.WHITE);

        xAxis.setTextSize(11f);


    }


    private void initDataLoad(){
        entryViewModel.getGroupEntriesThisMonth().observe(getViewLifecycleOwner(), this::updateChartData);
    }

    private void updateChartData(List<Entry> list){
        barEntries = Parser.listToBarEntries(list);
        barDataSet = new BarDataSet(barEntries, "");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setValueTextSize(13f);
        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getBarLabel(BarEntry barEntry) {
                return super.getBarLabel(barEntry);
            }
        });
        barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.invalidate();
    }

    private void setupSeekBar(){
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
                seekBarControl(progress);
            }
        });
    }


    private void seekBarControl(int progress) {
        if (progress == 3) {
            Log.i("mig", "My Month");
            title.setText(R.string.my_month);
            entryViewModel.getMyEntriesThisMonth().observe(getViewLifecycleOwner(), this::updateChartData);
        }
        else if (progress ==2 ) {
            Log.i("mig", "All Month");
            title.setText(R.string.all_time);
            entryViewModel.getEntriesByGroup().observe(getViewLifecycleOwner(), this::updateChartData);

        }
        else if (progress == 1) {
            Log.i("mig", "Last Month");
            title.setText(R.string.last_month);
            entryViewModel.getGroupEntriesLastMonth().observe(getViewLifecycleOwner(), this::updateChartData);
        }
        else {
            Log.i("mig", "Current Month");
            title.setText(R.string.current_month);
            entryViewModel.getGroupEntriesThisMonth().observe(getViewLifecycleOwner(), this::updateChartData);
        }
    }


}
