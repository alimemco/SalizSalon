package com.alirnp.salizsalon.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.CustomViews.MyTextView;
import com.alirnp.salizsalon.Model.Hour;
import com.alirnp.salizsalon.R;

import java.util.List;


public class HoursAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Hour> models;
    private OnItemClickListener onItemClickListener;


    public HoursAdapter(List<Hour> models) {
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

        mHolder.bind(position, onItemClickListener);

    }


    @Override
    public int getItemCount() {
        return models == null ? 0 : models.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void changeState(int position) {

        for (int i = 0; i < models.size(); i++) {
            this.models.get(i).setSelected(i == position);
        }

        notifyDataSetChanged();

    }

    public interface OnItemClickListener {
        void OnHourClick(Hour hour);
    }

    public class HoursHolder extends RecyclerView.ViewHolder {

        MyTextView hourTv;

        HoursHolder(View itemView) {
            super(itemView);

            hourTv = itemView.findViewById(R.id.rcv_hours_tv);

        }

        void bind(final int position, final OnItemClickListener onItemClickListener) {
            final Hour hour = models.get(position);

            itemView.setSelected(hour.isSelected());
            hourTv.setText(hour.getTime());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeState(position);
                    if (onItemClickListener != null) {
                        onItemClickListener.OnHourClick(hour);
                    }
                }
            });
        }
    }
}

