package com.alirnp.salizsalon.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.CustomViews.MyTextView;
import com.alirnp.salizsalon.Model.Day;
import com.alirnp.salizsalon.R;

import java.util.ArrayList;


public class DaysAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Day> models;
    private OnItemClickListener onItemClickListener;

    public DaysAdapter(ArrayList<Day> models) {
        this.models = models;
        changeState(0);

    }

    public ArrayList<Day> getModels() {
        return models;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_days, parent, false);
        return new DaysHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        DaysHolder mHolder = (DaysHolder) holder;

        mHolder.bind(position, onItemClickListener);

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

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void OnDayClick(Day day);
    }

    public class DaysHolder extends RecyclerView.ViewHolder {

        MyTextView dayName;
        MyTextView dayOfMonth;

        DaysHolder(View itemView) {
            super(itemView);

            dayName = itemView.findViewById(R.id.rcv_days_dayName);
            dayOfMonth = itemView.findViewById(R.id.rcv_days_dayOfMonth);

        }

        void bind(final int position, final OnItemClickListener onItemClickListener) {

            final Day day = models.get(position);

            dayName.setText(day.getDayName());

            String month = day.getDayOfMonth() + " " + day.getMonthName();
            dayOfMonth.setText(month);


            itemView.setSelected(day.isSelected());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    changeState(position);

                    if (onItemClickListener != null) {
                        onItemClickListener.OnDayClick(day);
                    }

                }
            });

        }
    }
}

