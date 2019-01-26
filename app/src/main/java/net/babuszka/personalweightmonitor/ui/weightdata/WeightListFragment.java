package net.babuszka.personalweightmonitor.ui.weightdata;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import net.babuszka.personalweightmonitor.R;
import net.babuszka.personalweightmonitor.common.error_handling.SaveWeightStatus;
import net.babuszka.personalweightmonitor.data.model.Weight;
import net.babuszka.personalweightmonitor.ui.dashboard.AddWeightViewModel;
import net.babuszka.personalweightmonitor.utils.ViewUtils;

import java.time.LocalDate;
import java.util.List;

public class WeightListFragment extends Fragment {

    private static final String TAG = "WeightListFragment";

    private WeightListViewModel weightListViewModel;
    private AddWeightViewModel addWeightViewModel;
    private EditWeightViewModel editWeightViewModel;
    private final WeightAdapter weightAdapter = new WeightAdapter();

    private View view;
    private RecyclerView recyclerView;
    private Dialog dialogWeight;
    private FloatingActionButton btnAddWeight;
    private DatePicker datePicker;
    private EditText etWeight;
    private Button btnSaveData;
    private Button btnCancel;

    private int currentlyEditedId;
    private boolean isWeightBeingEdited = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "[onCreateView] Started");

        view = (View) inflater.inflate(R.layout.fragment_weight_list, container, false);
        initView();
        setListeners();

        datePicker.setMaxDate(System.currentTimeMillis());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(weightAdapter);

        weightListViewModel = ViewModelProviders.of(this).get(WeightListViewModel.class);
        weightListViewModel.getAllWeight().observe(this, new Observer<List<Weight>>() {
            @Override
            public void onChanged(@Nullable List<Weight> weightList) {
                Log.d(TAG, "[onChanged] called");
                weightAdapter.setWeightList(weightList);
            }
        });

        addWeightViewModel = ViewModelProviders.of(this).get(AddWeightViewModel.class);
        addWeightViewModel.getStatus().observe(this, new Observer<SaveWeightStatus>() {
            @Override
            public void onChanged(@Nullable SaveWeightStatus status) {
                handleAddStatus(status);
            }
        });

        editWeightViewModel = ViewModelProviders.of(this).get(EditWeightViewModel.class);
        editWeightViewModel.getStatus().observe(this, new Observer<SaveWeightStatus>() {
            @Override
            public void onChanged(@Nullable SaveWeightStatus status) {
                handleEditStatus(status);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                weightListViewModel.delete(weightAdapter.getWeightAtPosition(viewHolder.getAdapterPosition()));
                ViewUtils.snackbarMessage(getView(), getString(R.string.message_status_weight_delete_success));
            }
        }).attachToRecyclerView(recyclerView);



        return view;
    }


    public void initView() {
        recyclerView = view.findViewById(R.id.recycler_view_weight);
        dialogWeight = new Dialog(getContext());
        dialogWeight.setContentView(R.layout.dialog_weight);
        btnAddWeight = view.findViewById(R.id.btnAdd);
        btnCancel = dialogWeight.findViewById(R.id.btnCancel);
        btnSaveData = dialogWeight.findViewById(R.id.btnSaveData);
        etWeight = dialogWeight.findViewById(R.id.etWeight);
        datePicker = dialogWeight.findViewById(R.id.datePicker);
    }

    public void setListeners() {

        btnAddWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "btnAddWeight clicked");
                isWeightBeingEdited = false;
                setLayoutValuesToDefault();
                dialogWeight.show();
            }
        });

        btnSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "btnSaveData clicked");
                int year = datePicker.getYear();
                int month = datePicker.getMonth()+1;
                int day = datePicker.getDayOfMonth();
                String weight = etWeight.getText().toString();
                if(isWeightBeingEdited)
                    editWeightViewModel.saveWeightButtonClicked(year, month, day, weight, currentlyEditedId);
                else
                    addWeightViewModel.saveWeightButtonClicked(year, month, day, weight);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "btnCancel clicked");
                editWeightViewModel.cancelWeightButtonClicked();
            }
        });

        weightAdapter.setOnItemClickListener(new WeightAdapter.OnItemClickListener() {

            @Override
            public void OnItemClick(Weight weight) {
                Log.d(TAG, "Weight Item on RecyclerView clicked");
                isWeightBeingEdited = true;
                etWeight.setText(weight.getWeight().toString());
                int year = weight.getDate().getYear();
                int month = weight.getDate().getMonthValue()-1;
                int day = weight.getDate().getDayOfMonth();
                datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                    }
                });
                currentlyEditedId = weight.getId();
                dialogWeight.show();
            }
        });

    }

    private void handleEditStatus(SaveWeightStatus status) {
        switch (status) {
            case SUCCESS: {
                ViewUtils.snackbarMessage(getView(), getString(R.string.message_status_weight_edit_success));
                dialogWeight.dismiss();
            } break;

            case EMPTY: {
                ViewUtils.toastMessage(getContext(), getString(R.string.message_status_weight_empty));
            } break;

            case NOT_A_NUMBER: {
                ViewUtils.toastMessage(getContext(), getString(R.string.message_status_weight_not_number));
            } break;

            case NEGATIVE_NUMBER: {
                ViewUtils.toastMessage(getContext(), getString(R.string.message_status_weight_negative_number));
            } break;

            case CANCELED: {
                dialogWeight.dismiss();
            } break;
        }

    }

    private void handleAddStatus(SaveWeightStatus status) {
        switch (status) {
            case SUCCESS: {
                ViewUtils.snackbarMessage(getView(), getString(R.string.message_status_weight_success));
                dialogWeight.dismiss();
            } break;

            case EMPTY: {
                ViewUtils.toastMessage(getContext(), getString(R.string.message_status_weight_empty));
            } break;

            case NOT_A_NUMBER: {
                ViewUtils.toastMessage(getContext(), getString(R.string.message_status_weight_not_number));
            } break;

            case NEGATIVE_NUMBER: {
                ViewUtils.toastMessage(getContext(), getString(R.string.message_status_weight_negative_number));
            } break;

            case CANCELED: {
                dialogWeight.dismiss();
            } break;
        }

    }

    private void setLayoutValuesToDefault() {
        this.etWeight.setText("");
        this.datePicker.updateDate(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue()-1, LocalDate.now().getDayOfMonth());
    }
}
