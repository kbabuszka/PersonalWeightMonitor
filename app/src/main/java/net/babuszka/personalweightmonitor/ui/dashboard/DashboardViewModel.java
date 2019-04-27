package net.babuszka.personalweightmonitor.ui.dashboard;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import net.babuszka.personalweightmonitor.data.WeightRepository;
import net.babuszka.personalweightmonitor.data.model.Weight;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DashboardViewModel extends AndroidViewModel {
    private static final String TAG = "DashboardViewModel";

    private WeightRepository weightRepository;
    private List<Weight> allWeightAsync;
    private MutableLiveData<Double> currentWeight = new MutableLiveData<>();
    private MutableLiveData<Double> totalWeightLoss = new MutableLiveData<>();
    private MutableLiveData<Double> totalWeightGain = new MutableLiveData<>();
    private MutableLiveData<Double> maxWeightLoss = new MutableLiveData<>();
    private MutableLiveData<Double> maxWeightGain = new MutableLiveData<>();
    private MutableLiveData<Integer> entriesAmount = new MutableLiveData<>();
    private MutableLiveData<Double> avgWeightChange = new MutableLiveData<>();
    private MutableLiveData<LocalDate> firstEntryDate = new MutableLiveData<>();

    public DashboardViewModel(@NonNull Application application) throws ExecutionException, InterruptedException {
        super(application);
        weightRepository = new WeightRepository(application);
        allWeightAsync = weightRepository.getAllWeightAsync();
    }

    public LiveData<Double> getCurrentWeight() {
        if(!allWeightAsync.isEmpty()) {
            Double current = allWeightAsync.get(0).getWeight();
            currentWeight.setValue(current);
        }
        return currentWeight;
    }

    public LiveData<Double> getTotalWeightLoss() {
        if(!allWeightAsync.isEmpty()) {
            Double diff = 0.0;
            Double loss = 0.0;
            for (int i = 0; i < allWeightAsync.size() - 1; i++) {
                diff = allWeightAsync.get(i).getWeight() - allWeightAsync.get(i + 1).getWeight();
                if (diff < 0)
                    loss += diff;
            }
            totalWeightLoss.setValue(loss);
        }
        return totalWeightLoss;
    }

    public LiveData<Double> getTotalWeightGain() {
        if(!allWeightAsync.isEmpty()) {
            Double diff = 0.0;
            Double gain = 0.0;
            for (int i = 0; i < allWeightAsync.size() - 1; i++) {
                diff = allWeightAsync.get(i).getWeight() - allWeightAsync.get(i + 1).getWeight();
                if (diff > 0)
                    gain += diff;
            }
            totalWeightGain.setValue(gain);
        }
        return totalWeightGain;
    }

    public LiveData<Double> getMaxWeightLoss() {
        if(!allWeightAsync.isEmpty()) {
            Double diff = 0.0;
            Double maxDiff = 0.0;
            for (int i = 0; i < allWeightAsync.size() - 1; i++) {
                diff = allWeightAsync.get(i).getWeight() - allWeightAsync.get(i + 1).getWeight();
                if (diff < maxDiff)
                    maxDiff = diff;
            }
            maxWeightLoss.setValue(maxDiff);
        }
        return maxWeightLoss;
    }

    public LiveData<Double> getMaxWeightGain() {
        if(!allWeightAsync.isEmpty()) {
            Double diff = 0.0;
            Double maxDiff = 0.0;
            for (int i = 0; i < allWeightAsync.size() - 1; i++) {
                diff = allWeightAsync.get(i).getWeight() - allWeightAsync.get(i + 1).getWeight();
                if (diff > maxDiff)
                    maxDiff = diff;
            }
            maxWeightGain.setValue(maxDiff);
        }
        return maxWeightGain;
    }

    public MutableLiveData<Integer> getEntriesAmount() {
        entriesAmount.setValue(allWeightAsync.size());
        return entriesAmount;
    }

    public LiveData<Double> getAvgWeightChange() {
        if(!allWeightAsync.isEmpty() || allWeightAsync.size() < 3) {
            Double diff = 0.0;
            Double totalDiff = 0.0;
            int diffCount = 0;
            for (int i = 0; i < allWeightAsync.size() - 1; i++) {
                diff = Math.abs(allWeightAsync.get(i).getWeight() - allWeightAsync.get(i + 1).getWeight());
                if (diff != 0) {
                    totalDiff += diff;
                    diffCount++;
                }
            }
            Double avg = totalDiff / diffCount;
            avgWeightChange.setValue(avg);
        }
        return avgWeightChange;
    }

    public LiveData<LocalDate> getFirstEntryDate() {
        if(!allWeightAsync.isEmpty()) {
            LocalDate date = allWeightAsync.get(allWeightAsync.size() - 1).getDate();
            firstEntryDate.setValue(date);
        }
        return firstEntryDate;
    }
}