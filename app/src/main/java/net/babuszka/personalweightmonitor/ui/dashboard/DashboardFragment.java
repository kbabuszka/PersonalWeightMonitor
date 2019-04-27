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
import android.widget.TextView;

import net.babuszka.personalweightmonitor.R;
import net.babuszka.personalweightmonitor.common.error_handling.SaveWeightStatus;
import net.babuszka.personalweightmonitor.data.model.Weight;
import net.babuszka.personalweightmonitor.utils.MessageTypes;
import net.babuszka.personalweightmonitor.utils.ViewUtils;

import java.time.LocalDate;
import java.util.List;

public class DashboardFragment extends Fragment {

    private static final String TAG = "DashboardFragment";

    private DashboardViewModel dashboardViewModel;
    private AddWeightViewModel addWeightViewModel;

    private View view;
    private FloatingActionButton btnAddWeight;
    private Dialog dialogWeight;
    private DatePicker datePicker;
    private EditText etWeight;
    private Button btnSaveData;
    private Button btnCancel;
    private TextView tvCurrentWeight;
    private TextView tvTotalWeightLoss;
    private TextView tvTotalWeightGain;
    private TextView tvMaxWeightLoss;
    private TextView tvMaxWeightGain;
    private TextView tvEntriesAmount;
    private TextView tvAverageWeightChange;
    private TextView tvTogetherSince;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "[onCreateView] Started");

        view = (View) inflater.inflate(R.layout.fragment_dashboard, container, false);
        initView();
        setListeners();
        datePicker.setMaxDate(System.currentTimeMillis());
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);

        dashboardViewModel.getCurrentWeight().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double currentWeight) {
                tvCurrentWeight.setText(String.format("%.1f", currentWeight));
            }
        });

        dashboardViewModel.getTotalWeightLoss().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double totalWeightLoss) {
                tvTotalWeightLoss.setText(String.format("%.1f", totalWeightLoss));
            }
        });

        dashboardViewModel.getTotalWeightGain().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double totalWeightGain) {
                tvTotalWeightGain.setText(String.format("%.1f", totalWeightGain));
            }
        });

        dashboardViewModel.getMaxWeightLoss().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double maxWeightLoss) {
                tvMaxWeightLoss.setText(String.format("%.1f", maxWeightLoss));
            }
        });

        dashboardViewModel.getMaxWeightGain().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double maxWeightGain) {
                tvMaxWeightGain.setText(String.format("%.1f", maxWeightGain));
            }
        });

        dashboardViewModel.getEntriesAmount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer entriesAmount) {
                tvEntriesAmount.setText(entriesAmount.toString());
            }
        });

        dashboardViewModel.getAvgWeightChange().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double avgWeightChange) {
                tvAverageWeightChange.setText(String.format("%.1f", avgWeightChange));
            }
        });

        dashboardViewModel.getFirstEntryDate().observe(this, new Observer<LocalDate>() {
            @Override
            public void onChanged(@Nullable LocalDate firstEntryDate) {
                tvTogetherSince.setText(firstEntryDate.toString());
            }
        });

        addWeightViewModel = ViewModelProviders.of(this).get(AddWeightViewModel.class);
        addWeightViewModel.getStatus().observe(this, new Observer<SaveWeightStatus>() {
            @Override
            public void onChanged(@Nullable SaveWeightStatus status) {
                handleStatus(status);
            }
        });

        return view;
    }

    private void initView() {
        btnAddWeight = view.findViewById(R.id.btnAdd);
        dialogWeight = new Dialog(getContext());
        dialogWeight.setContentView(R.layout.dialog_weight);
        btnCancel = dialogWeight.findViewById(R.id.btnCancel);
        btnSaveData = dialogWeight.findViewById(R.id.btnSaveData);
        etWeight = dialogWeight.findViewById(R.id.etWeight);
        datePicker = dialogWeight.findViewById(R.id.datePicker);
        tvCurrentWeight = view.findViewById(R.id.text_current_weight);
        tvTotalWeightLoss = view.findViewById(R.id.text_total_weight_loss);
        tvTotalWeightGain = view.findViewById(R.id.text_total_weight_gain);
        tvMaxWeightLoss = view.findViewById(R.id.text_max_weight_loss);
        tvMaxWeightGain = view.findViewById(R.id.text_max_weight_gain);
        tvEntriesAmount = view.findViewById(R.id.text_number_of_entries);
        tvAverageWeightChange = view.findViewById(R.id.text_avg_change);
        tvTogetherSince = view.findViewById(R.id.text_together_since);
    }

    private void setListeners() {

        btnAddWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "btnAddWeight clicked");
                setLayoutValuesToDefault();
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
                ViewUtils.snackbarMessage(getView(), getString(R.string.message_status_weight_success));
                dialogWeight.dismiss();
            } break;

            case EMPTY: {
                ViewUtils.toastMessage(getContext(), getString(R.string.message_status_weight_empty), MessageTypes.ERROR);
            } break;

            case NOT_A_NUMBER: {
                ViewUtils.toastMessage(getContext(), getString(R.string.message_status_weight_not_number), MessageTypes.ERROR);
            } break;

            case NEGATIVE_NUMBER: {
                ViewUtils.toastMessage(getContext(), getString(R.string.message_status_weight_negative_number), MessageTypes.ERROR);
            } break;

            case CANCELED: {
                dialogWeight.dismiss();
            } break;
        }

    }

    private void setLayoutValuesToDefault() {
        this.etWeight.setText("");
        this.datePicker.updateDate(LocalDate.now().getYear(), LocalDate.now().getMonthValue()-1, LocalDate.now().getDayOfMonth());
    }

}