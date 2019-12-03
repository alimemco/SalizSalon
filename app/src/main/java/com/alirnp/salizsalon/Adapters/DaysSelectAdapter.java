package com.alirnp.salizsalon.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.CustomViews.MyTextView;
import com.alirnp.salizsalon.Model.Day;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Utils;

import java.util.List;


public class DaysSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Day> models;


    public DaysSelectAdapter(List<Day> models) {
        this.models = models;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_7_day, parent, false);
        return new DaysSelectHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof DaysSelectHolder) {
            DaysSelectHolder mHolder = (DaysSelectHolder) holder;
            mHolder.bind(position);
        }


    }


    @Override
    public int getItemCount() {
        return models == null ? 0 : models.size();
    }

    private void changeState(int position) {

        for (int i = 0; i < models.size(); i++) {
            this.models.get(i).setSelected(i == position);
        }

        notifyDataSetChanged();

    }

    public class DaysSelectHolder extends RecyclerView.ViewHolder {

        private MyTextView dayTextView;

        DaysSelectHolder(View itemView) {
            super(itemView);
            dayTextView = itemView.findViewById(R.id.rcv_7_day_dayName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeState(getAdapterPosition());
                }
            });

        }

        void bind(int position) {
            Day day = models.get(position);
            dayTextView.setText(Utils.convertDayNameToPersian(day.getDayName()));
            itemView.setSelected(day.isSelected());
        }


    }
}

