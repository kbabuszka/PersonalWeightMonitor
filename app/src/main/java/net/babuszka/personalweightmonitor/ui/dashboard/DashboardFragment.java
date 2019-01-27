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
import net.babuszka.personalweightmonitor.utils.MessageTypes;
import net.babuszka.personalweightmonitor.utils.ViewUtils;

import java.time.LocalDate;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "[onCreateView] Started");

        view = (View) inflater.inflate(R.layout.fragment_dashboard, container, false);
        initView();
        setListeners();
        datePicker.setMaxDate(System.currentTimeMillis());
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);

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
