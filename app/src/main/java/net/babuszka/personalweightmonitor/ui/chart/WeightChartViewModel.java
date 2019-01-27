package net.babuszka.personalweightmonitor.ui.chart;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.github.mikephil.charting.data.Entry;

import net.babuszka.personalweightmonitor.data.WeightRepository;
import net.babuszka.personalweightmonitor.data.model.Weight;

import java.util.ArrayList;
import java.util.List;

public class WeightChartViewModel extends AndroidViewModel {
    private static final String TAG = "WeightChartViewModel";

    private WeightRepository weightRepository;
    private LiveData<List<Weight>> allWeight;

    public WeightChartViewModel(@NonNull Application application) {
        super(application);
        weightRepository = new WeightRepository(application);
        allWeight = weightRepository.getAllWeight();
    }

    public LiveData<List<Weight>> getAllWeight() {
        return allWeight;
    }

    public List<Entry> getChartData(List<Weight> weightList) {
        List<Entry> entries = new ArrayList<Entry>();
        for (Weight weightItem : weightList) {
            //entries.add(new Entry(weightItem.getWeight(), weightItem.getDate()));
            entries.add(new Entry(Float.parseFloat("1"), weightItem.getWeight().floatValue()));
        }
        return entries;
    }
}
