package net.babuszka.personalweightmonitor.ui.dashboard;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import net.babuszka.personalweightmonitor.data.WeightRepository;
import net.babuszka.personalweightmonitor.data.model.Weight;

import java.util.Date;


public class DashboardViewModel extends AndroidViewModel {
    private static final String TAG = "DashboardViewModel";

    private WeightRepository weightRepository;

    private MutableLiveData addWeightResponse;

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        weightRepository = new WeightRepository(application);
    }






}
