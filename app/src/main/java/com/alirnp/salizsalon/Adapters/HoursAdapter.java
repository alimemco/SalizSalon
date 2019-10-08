package com.alirnp.salizsalon.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.CustomViews.MyTextView;
import com.alirnp.salizsalon.R;

import java.util.List;


public class HoursAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> models;


    public HoursAdapter(List<String> models) {
        this.models = models;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_hours, parent, false);
        return new HoursHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        HoursHolder mHolder = (HoursHolder) holder;

        mHolder.bind(position);

    }


    @Override
    public int getItemCount() {
        return models == null ? 0 : models.size();
    }

    public class HoursHolder extends RecyclerView.ViewHolder {

        MyTextView hourTv;

        HoursHolder(View itemView) {
            super(itemView);

            hourTv = itemView.findViewById(R.id.rcv_hours_tv);

        }

        void bind(int position) {
            hourTv.setText(models.get(position));
        }
    }
}

