package net.babuszka.personalweightmonitor.ui.chart;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.utils.EntryXComparator;

import net.babuszka.personalweightmonitor.R;
import net.babuszka.personalweightmonitor.data.model.Weight;
import net.babuszka.personalweightmonitor.ui.weightdata.WeightAdapter;

import java.util.Collections;
import java.util.List;

public class ChartFragment extends Fragment {
    private static final String TAG = "ChartFragment";

    private WeightChartViewModel chartViewModel;
    //private final WeightAdapter weightAdapter = new WeightAdapter();
    private List<Entry> chartData;

    private View view;
    private LineChart chart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "[onCreateView] Started");

        view = (View) inflater.inflate(R.layout.fragment_chart, container, false);
        initView();
        setListeners();

        chartViewModel = ViewModelProviders.of(this).get(WeightChartViewModel.class);
        chartViewModel.getAllWeight().observe(this, new Observer<List<Weight>>() {
            @Override
            public void onChanged(@Nullable List<Weight> weightList) {
                chartData = chartViewModel.getChartData(weightList);
                prepareChart(chartData);
            }
        });
        return view;
    }

    private void initView() {
        chart = view.findViewById(R.id.chart_weight);
    }

    private void setListeners() {

    }

    private void prepareChart(List<Entry> chartData) {
        Collections.sort(chartData, new EntryXComparator()); // List must be sorted per MPchart documentation
        LineDataSet dataSet = new LineDataSet(chartData, "kg");
        dataSet.setColor(Color.RED);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();
    }

}
