package net.babuszka.personalweightmonitor.ui.weightdata;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.babuszka.personalweightmonitor.R;
import net.babuszka.personalweightmonitor.data.model.Weight;

import java.util.ArrayList;
import java.util.List;

public class WeightAdapter extends RecyclerView.Adapter<WeightAdapter.WeightViewHolder> {

    private List<Weight> weightList = new ArrayList<>();

    @NonNull
    @Override
    public WeightViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weight_item, viewGroup, false);
        return new WeightViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeightViewHolder weightViewHolder, int i) {
        Weight weightItem = weightList.get(i);
        weightViewHolder.tvWeight.setText(weightItem.getWeight().toString());
        weightViewHolder.tvDate.setText(weightItem.getDate().toString());
    }

    @Override
    public int getItemCount() {
        return weightList.size();
    }

    public void setWeightList(List<Weight> weightList) {
        this.weightList = weightList;
        notifyDataSetChanged();
    }

    class WeightViewHolder extends RecyclerView.ViewHolder {
        private TextView tvWeight;
        private TextView tvDate;

        public WeightViewHolder(View itemView) {
            super(itemView);
            tvWeight = itemView.findViewById(R.id.text_weight);
            tvDate = itemView.findViewById(R.id.text_date);
        }
    }
}