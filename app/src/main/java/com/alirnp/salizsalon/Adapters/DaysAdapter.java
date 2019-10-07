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

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;


public class DaysAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Day> models;


    public DaysAdapter(ArrayList<Day> models) {
        this.models = models;
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

        mHolder.bind(models.get(position));

    }


    @Override
    public int getItemCount() {
        return models == null ? 0 : models.size();
    }

    public static class DaysHolder extends RecyclerView.ViewHolder {

        MyTextView dayName;
        MyTextView dayOfMonth;

        PersianDate pDate;
        PersianDateFormat pFormatter;

        DaysHolder(View itemView) {
            super(itemView);

            dayName = itemView.findViewById(R.id.rcv_days_dayName);
            dayOfMonth = itemView.findViewById(R.id.rcv_days_dayOfMonth);

            pDate = new PersianDate();
            pFormatter = new PersianDateFormat("j");
        }

        void bind(Day day) {
            dayName.setText(day.getDayName());

            String month = day.getDayOfMonth() + " " + day.getMonthName();
            dayOfMonth.setText(month);


        }
    }
}

