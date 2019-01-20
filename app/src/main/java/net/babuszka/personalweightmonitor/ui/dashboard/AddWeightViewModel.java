package net.babuszka.personalweightmonitor.ui.dashboard;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import net.babuszka.personalweightmonitor.common.error_handling.SaveWeightStatus;
import net.babuszka.personalweightmonitor.common.error_handling.SingleLiveEvent;
import net.babuszka.personalweightmonitor.data.WeightRepository;
import net.babuszka.personalweightmonitor.data.model.Weight;

import java.util.Date;

public class AddWeightViewModel extends AndroidViewModel {
    private static final String TAG = "DashboardViewModel";

    private WeightRepository weightRepository;
    private SingleLiveEvent<SaveWeightStatus> status;

    public AddWeightViewModel(@NonNull Application application) {
        super(application);
        status = new SingleLiveEvent<SaveWeightStatus>();
        weightRepository = new WeightRepository(application);
    }

    public LiveData<SaveWeightStatus> getStatus() {
        return this.status;
    }


    public void insert(Weight weight) {
        weightRepository.insert(weight);
    }


    public void saveWeightButtonClicked(int year, int month, int day, String weight) {
        Log.d(TAG, "[saveWeightButtonClicked] Received following data from activity: " + year + "-" + month + "-" + day + ", Weight: " + weight);

        if (weight.length() > 0) {
            Double dWeight = Double.parseDouble(weight);
            Date date = new Date(year, month, day);
            Weight newWeight = new Weight(dWeight, date);
            insert(newWeight);
            status.setValue(SaveWeightStatus.SUCCESS);
            Log.d(TAG, "[saveWeightButtonClicked] Inserted following data: " + date.toString());
        } else {
            status.setValue(SaveWeightStatus.EMPTY);
        }
    }

    public void cancelWeightButtonClicked() {
        status.setValue(SaveWeightStatus.CANCELED);
        Log.d(TAG, "[cancelWeightButtonClicked] called");

    }



}
