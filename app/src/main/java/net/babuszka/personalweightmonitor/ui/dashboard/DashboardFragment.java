package net.babuszka.personalweightmonitor.ui.dashboard;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import net.babuszka.personalweightmonitor.R;
import net.babuszka.personalweightmonitor.common.error_handling.SaveWeightStatus;
import net.babuszka.personalweightmonitor.utils.ViewUtils;

public class DashboardFragment extends Fragment {

    private static final String TAG = "DashboardFragment";

    private DashboardViewModel dashboardViewModel;
    private AddWeightViewModel addWeightViewModel;

    private View view;
    private FloatingActionButton btnAdd;
    private Dialog dialogWeight;
    private DatePicker datePicker;
    private EditText etWeight;
    private Button btnSaveData;
    private Button btnCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "[onCreateView] Started");

        view = (View) inflater.inflate(R.layout.fragment_dashboard, container, false);
        initView();
        setListeners();
        datePicker.setMaxDate(System.currentTimeMillis());
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);

        addWeightViewModel = ViewModelProviders.of(this).get(AddWeightViewModel.class);
        Log.d(TAG, "Is addWeightViewModel a null? " + addWeightViewModel.equals(null));
        addWeightViewModel.getStatus().observe(this, new Observer<SaveWeightStatus>() {
            @Override
            public void onChanged(@Nullable SaveWeightStatus status) {
                handleStatus(status);
            }
        });
        return view;
    }

    public void initView() {
        btnAdd = view.findViewById(R.id.btnAdd);

        dialogWeight = new Dialog(getContext());
        dialogWeight.setContentView(R.layout.dialog_weight);
        btnCancel = dialogWeight.findViewById(R.id.btnCancel);
        btnSaveData = dialogWeight.findViewById(R.id.btnSaveData);
        etWeight = dialogWeight.findViewById(R.id.etWeight);
        datePicker = dialogWeight.findViewById(R.id.datePicker);
    }

    public void setListeners() {

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "btnAdd clicked");
                dialogWeight.show();
            }
        });

        btnSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = datePicker.getYear();
                int month = datePicker.getMonth()+1;
                int day = datePicker.getDayOfMonth();
                String weight = etWeight.getText().toString();
                addWeightViewModel.saveWeightButtonClicked(year, month, day, weight);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWeightViewModel.cancelWeightButtonClicked();
            }
        });
    }

    private void handleStatus(SaveWeightStatus status) {
        switch (status) {
            case SUCCESS: {
                ViewUtils.toastMessage(getContext(), "Waga dodana");
                dialogWeight.dismiss();
            } break;

            case EMPTY: {
                ViewUtils.toastMessage(getContext(), "Musisz podac wage");
            } break;

            case NOT_A_NUMBER: {
                ViewUtils.toastMessage(getContext(), "Podana waga musi być liczbą");
            } break;

            case NEGATIVE_NUMBER: {
                ViewUtils.toastMessage(getContext(), "Waga nie może być ujemna");
            } break;

            case CANCELED: {
                dialogWeight.dismiss();
            } break;
        }

    }

}
