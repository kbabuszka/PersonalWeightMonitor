package net.babuszka.personalweightmonitor.ui.weightdata;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.babuszka.personalweightmonitor.R;
import net.babuszka.personalweightmonitor.data.model.Weight;

import java.util.List;

public class WeightListFragment extends Fragment {

    private static final String TAG = "WeightListFragment";
    private WeightListViewModel weightListViewModel;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "[onCreateView] Started");
        View view = (View) inflater.inflate(R.layout.fragment_weight_list, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_weight);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final WeightAdapter weightAdapter = new WeightAdapter();
        recyclerView.setAdapter(weightAdapter);

        weightListViewModel = ViewModelProviders.of(this).get(WeightListViewModel.class);
        weightListViewModel.getAllWeight().observe(this, new Observer<List<Weight>>() {
            @Override
            public void onChanged(@Nullable List<Weight> weightList) {
                Log.d(TAG, "[onChanged] called");
                weightAdapter.setWeightList(weightList);
            }
        });
        return view;
    }
}
