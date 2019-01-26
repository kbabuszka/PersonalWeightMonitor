package net.babuszka.personalweightmonitor.ui.weightdata;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.babuszka.personalweightmonitor.R;
import net.babuszka.personalweightmonitor.data.model.Weight;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class WeightAdapter extends RecyclerView.Adapter<WeightAdapter.WeightViewHolder> {

    private List<Weight> weightList = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public WeightViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weight_item, viewGroup, false);
        return new WeightViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeightViewHolder weightViewHolder, int i) {
        Weight weightItem = weightList.get(i);
        weightViewHolder.tvWeight.setText((weightItem.getWeight() == null) ? "" : weightItem.getWeight().toString() + " kg");
        weightViewHolder.tvDate.setText(weightItem.getDate().toString());

        if(i<weightList.size()-1) {
            Weight nextWeightItem = weightList.get(i+1);
            if(nextWeightItem != null) {
                Double change = weightItem.getWeight() - nextWeightItem.getWeight();
                weightViewHolder.tvWeightChange.setText((nextWeightItem != null) ? String.format("%.1f", change) : "");

                if(change > 0) {
                    weightViewHolder.tvWeightChange.setTextColor(Color.parseColor("#FF0000"));
                    weightViewHolder.imageChangeArrow.setImageResource(R.drawable.ic_arrow_red_24dp);

                }

                if(change < 0) {
                    weightViewHolder.tvWeightChange.setTextColor(Color.parseColor("#32a000"));
                    weightViewHolder.imageChangeArrow.setImageResource(R.drawable.ic_arrow_green_24dp);
                    weightViewHolder.imageChangeArrow.setRotation((float) 90.0);
                }

                if(change == 0) {
                    weightViewHolder.tvWeightChange.setTextColor(Color.parseColor("#000000"));
                    weightViewHolder.imageChangeArrow.setVisibility(View.INVISIBLE);
                }
            }
        } else {
            weightViewHolder.tvWeightChange.setText("");
            weightViewHolder.imageChangeArrow.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return weightList.size();
    }

    public void setWeightList(List<Weight> weightList) {
        this.weightList = weightList;
        notifyDataSetChanged();
    }

    public Weight getWeightAtPosition(int position) {
        return weightList.get(position);
    }

    class WeightViewHolder extends RecyclerView.ViewHolder {
        private TextView tvWeight;
        private TextView tvDate;
        private TextView tvWeightChange;
        private ImageView imageChangeArrow;

        public WeightViewHolder(View itemView) {
            super(itemView);
            tvWeight = itemView.findViewById(R.id.text_weight);
            tvDate = itemView.findViewById(R.id.text_date);
            tvWeightChange = itemView.findViewById(R.id.text_weight_change);
            imageChangeArrow = itemView.findViewById(R.id.imageview_arrow_change);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        listener.OnItemClick(weightList.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(Weight weight);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
