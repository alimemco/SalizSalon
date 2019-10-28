package com.alirnp.salizsalon.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.CustomViews.MyTextView;
import com.alirnp.salizsalon.Model.Day;
import com.alirnp.salizsalon.Model.Hour;
import com.alirnp.salizsalon.Model.Service;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;


public class FinalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Service> models;
    private Day day;
    private Hour hour;


    public FinalAdapter(Day day, Hour hour, ArrayList<Service> services) {
        this.models = services;
        this.day = day;
        this.hour = hour;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0)
                ? Constants.viewType.TIME.getViewType()
                : Constants.viewType.SERVICES.getViewType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == Constants.viewType.TIME.getViewType()) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_final_time, parent, false);
            return new FinalTimeHolder(view, day, hour);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_final_services, parent, false);
            return new FinalServiceHolder(view);
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (position > 0) {
            FinalServiceHolder mHolder = (FinalServiceHolder) holder;
            mHolder.bind(position - 1);
        }

    }


    @Override
    public int getItemCount() {
        return models == null ? 1 : models.size() + 1;
    }

    public class FinalTimeHolder extends RecyclerView.ViewHolder {

        private MyTextView dayTv;
        private MyTextView hourTv;

        FinalTimeHolder(View itemView, Day day, Hour hour) {
            super(itemView);

            dayTv = itemView.findViewById(R.id.rcv_final_time_day);
            hourTv = itemView.findViewById(R.id.rcv_final_time_hour);

            String dayStr = day.getDayName() + " " + day.getDayOfMonth() + " " + day.getMonthName();
            String hourStr = " ساعت " + hour.getTime();

            dayTv.setText(dayStr);
            hourTv.setText(hourStr);
        }
    }

    public class FinalServiceHolder extends RecyclerView.ViewHolder {

        private MyTextView title;
        private MyTextView price;

        FinalServiceHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.rcv_final_services_title);
            price = itemView.findViewById(R.id.rcv_final_services_price);


        }

        void bind(int position) {

            Service service = models.get(position);
            NumberFormat format = new DecimalFormat("#,###,###");
            String prc = format.format(service.getPrice()) + " تومان ";

            title.setText(service.getName());
            price.setText(prc);

        }
    }
}

