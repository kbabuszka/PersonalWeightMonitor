package net.babuszka.personalweightmonitor.ui.dashboard;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import net.babuszka.personalweightmonitor.data.WeightRepository;
import net.babuszka.personalweightmonitor.data.model.Weight;

import java.util.Date;


public class DashboardViewModel extends AndroidViewModel {
    private static final String TAG = "DashboardViewModel";

    private WeightRepository weightRepository;

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        weightRepository = new WeightRepository(application);
    }

    public void saveWeightButtonClicked(int year, int month, int day, String weight) {
        Log.d(TAG, "[saveWeightButtonClicked] Received following data from activity: " + year + "-" + month + "-" + day + ", Weight: " + weight);

        if (weight.length() > 0) {
            Double dWeight = Double.parseDouble(weight);
            Date date = new Date(year, month, day);
            Weight newWeight = new Weight(dWeight, date);
            insert(newWeight);
            Log.d(TAG, "[saveWeightButtonClicked] Inserted following data: " + date.toString());
        }
    }

    public void insert(Weight weight) {
        weightRepository.insert(weight);
    }

}
