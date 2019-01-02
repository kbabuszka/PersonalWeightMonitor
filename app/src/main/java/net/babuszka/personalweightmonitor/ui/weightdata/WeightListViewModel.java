package net.babuszka.personalweightmonitor.ui.weightdata;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import net.babuszka.personalweightmonitor.data.WeightRepository;
import net.babuszka.personalweightmonitor.data.model.Weight;

import java.util.List;

public class WeightListViewModel extends AndroidViewModel {
    private static final String TAG = "WeightListViewModel";

    private WeightRepository weightRepository;
    private LiveData<List<Weight>> allWeight;

    public WeightListViewModel(@NonNull Application application) {
        super(application);
        weightRepository = new WeightRepository(application);
        allWeight = weightRepository.getAllWeight();
    }

    public void insert(Weight weight) {
        weightRepository.insert(weight);
    }

    public void update(Weight weight) {
        weightRepository.update(weight);
    }

    public void delete(Weight weight) {
        weightRepository.delete(weight);
    }

    public LiveData<List<Weight>> getAllWeight() {
        return allWeight;
    }

}
