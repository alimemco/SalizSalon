package com.alirnp.salizsalon.ADMIN.Adapter;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.CustomViews.MyTextView;
import com.alirnp.salizsalon.NestedJson.Item;
import com.alirnp.salizsalon.R;


public class MyAdapterTime extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item> models;

    public MyAdapterTime(List<Item> models) {
        this.models = models;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_manage_times, parent, false);
        return new MyTimeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyTimeHolder mHolder = (MyTimeHolder) holder;
        mHolder.bind(position);
    }

    public List<Item> getModels() {
        return models;
    }

    @Override
    public int getItemCount() {
        return models == null ? 0 : models.size();
    }

    class MyTimeHolder extends RecyclerView.ViewHolder {

        private MyTextView dayTv;

        public MyTimeHolder(View itemView) {
            super(itemView);
            dayTv = itemView.findViewById(R.id.rcv_manage_times_hour);
        }

        void bind(int position) {
            dayTv.setText("main : pos: " + position);

        }
    }
}
