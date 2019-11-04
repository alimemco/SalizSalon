package com.alirnp.salizsalon.Adapters;


import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.CustomViews.MyTextView;
import com.alirnp.salizsalon.Holder.NotFoundHolder;
import com.alirnp.salizsalon.Holder.SearchingHolder;
import com.alirnp.salizsalon.Model.Hour;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;

import java.util.List;


public class HoursAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Hour> models;
    private OnItemClickListener onItemClickListener;

    private Constants.state state;


    public HoursAdapter() {

    }


    public void setData(List<Hour> models, OnItemClickListener onItemClickListener) {
        state = Constants.state.SUCCESS;

        this.models = models;

        this.onItemClickListener = onItemClickListener;
        notifyDataSetChanged();

    }

    public void setSearching() {
        state = Constants.state.SEARCHING;
        notifyDataSetChanged();

    }

    public void setNotFound() {
        state = Constants.state.ITEM_NOT_FOUND;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        if (viewType == Constants.state.SUCCESS.getStatus()) {
            return new HoursHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_hours, parent, false));

        } else if (viewType == Constants.state.ITEM_NOT_FOUND.getStatus()) {
            return new NotFoundHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_not_found, parent, false));

        } else if (viewType == Constants.state.SEARCHING.getStatus()) {
            return new SearchingHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_searching, parent, false));

        } else {
            return new SearchingHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_searching, parent, false));
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if (state == Constants.state.SUCCESS) {
            HoursHolder mHolder = (HoursHolder) holder;
            mHolder.bind(position, onItemClickListener);
        }


    }


    @Override
    public int getItemViewType(int position) {

        switch (state) {
            case SUCCESS:
                return Constants.state.SUCCESS.getStatus();
            case ITEM_NOT_FOUND:
                return Constants.state.ITEM_NOT_FOUND.getStatus();
            case SEARCHING:
                return Constants.state.SEARCHING.getStatus();

            default:
                return Constants.state.SEARCHING.getStatus();
        }

    }

    @Override
    public int getItemCount() {

        switch (state) {
            case SUCCESS:
                return models == null ? 0 : models.size();

            case SEARCHING:
                return 1;

            case ITEM_NOT_FOUND:
                return 1;

            default:
                return 0;
        }

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
        MyTextView reservedTv;

        HoursHolder(View itemView) {
            super(itemView);

            hourTv = itemView.findViewById(R.id.rcv_hours_tv);
            reservedTv = itemView.findViewById(R.id.rcv_hours_reserved);

        }

        void bind(final int position, final OnItemClickListener onItemClickListener) {

            final Hour hour = models.get(position);

            itemView.setSelected(hour.isSelected());
            hourTv.setText(hour.getTime());
            reservedTv.setVisibility(hour.isReserved() ? View.VISIBLE : View.INVISIBLE);
            hourTv.setPaintFlags(hour.isReserved() ? hourTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG : 0);

            if (!hour.isReserved()) {

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


}

