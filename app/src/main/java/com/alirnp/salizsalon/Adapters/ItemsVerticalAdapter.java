package com.alirnp.salizsalon.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.CustomViews.MyTextView;
import com.alirnp.salizsalon.NestedJson.Result;
import com.alirnp.salizsalon.R;

import java.util.List;


public class ItemsVerticalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Result> models;


    public ItemsVerticalAdapter(List<Result> models) {
        this.models = models;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_items_ver, parent, false);
        return new ItemsVerticalHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ItemsVerticalHolder mHolder = (ItemsVerticalHolder) holder;
        mHolder.bind(models.get(position));


    }


    @Override
    public int getItemCount() {
        return models == null ? 0 : models.size();
    }

    public static class ItemsVerticalHolder extends RecyclerView.ViewHolder {

        MyTextView title;
        RecyclerView rcv;


        ItemsVerticalHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.rcv_items_ver_title);
            rcv = itemView.findViewById(R.id.rcv_items_ver_rcv);
            rcv.setLayoutManager(new LinearLayoutManager(itemView.getContext(), RecyclerView.HORIZONTAL, true));

        }

        void bind(Result result) {
            title.setText(result.getTitle());

            rcv.setAdapter(new ItemsHorizontalAdapter(result.getItems()));

        }
    }
}

