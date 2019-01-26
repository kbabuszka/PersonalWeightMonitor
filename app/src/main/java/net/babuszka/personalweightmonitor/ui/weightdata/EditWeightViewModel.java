package net.babuszka.personalweightmonitor.ui.weightdata;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import net.babuszka.personalweightmonitor.common.error_handling.SaveWeightStatus;
import net.babuszka.personalweightmonitor.common.error_handling.SingleLiveEvent;
import net.babuszka.personalweightmonitor.data.WeightRepository;
import net.babuszka.personalweightmonitor.data.model.Weight;

import java.time.LocalDate;

public class EditWeightViewModel extends AndroidViewModel {
    private static final String TAG = "EditWeightViewModel";

    private WeightRepository weightRepository;
    private SingleLiveEvent<SaveWeightStatus> status;

    public EditWeightViewModel(@NonNull Application application) {
        super(application);
        status = new SingleLiveEvent<>();
        weightRepository = new WeightRepository(application);
    }

    public LiveData<SaveWeightStatus> getStatus() {
        return this.status;
    }

    public void update(Weight weight) {
        weightRepository.update(weight);
    }

    public void saveWeightButtonClicked(int year, int month, int day, String weight, int id) {
        Log.d(TAG, "[saveWeightButtonClicked] Received following data from activity: ID: " + id + ", Date: " + year + "-" + month + "-" + day + ", Weight: " + weight);

        if (weight.length() > 0) {
            Double dWeight = Double.parseDouble(weight);
            LocalDate date = LocalDate.of(year, month, day);

            Weight newWeight = new Weight(dWeight, date);
            newWeight.setId(id);
            update(newWeight);
            status.setValue(SaveWeightStatus.SUCCESS);
            Log.d(TAG, "[saveWeightButtonClicked] Updated following data: " + date.toString());
        } else {
            status.setValue(SaveWeightStatus.EMPTY);
        }
    }

    public void cancelWeightButtonClicked() {
        status.setValue(SaveWeightStatus.CANCELED);
        Log.d(TAG, "[cancelWeightButtonClicked] called");

    }
}
