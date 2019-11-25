package com.alirnp.salizsalon.ADMIN.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.CustomViews.MyTextView;
import com.alirnp.salizsalon.R;
import com.h6ah4i.android.widget.advrecyclerview.headerfooter.AbstractHeaderFooterWrapperAdapter;

public class MyHeadFootAdapter extends AbstractHeaderFooterWrapperAdapter<MyHeadFootAdapter.HeaderVH, MyHeadFootAdapter.FooterVH> {

    public MyHeadFootAdapter(RecyclerView.Adapter adapter) {
        //super.this(adapter);
        //  super(adapter);
        super.setAdapter(adapter);
    }

    @Override
    public int getHeaderItemCount() {
        return 3;
    }

    @Override
    public int getFooterItemCount() {
        return 0;
    }

    @Override
    @NonNull
    public HeaderVH onCreateHeaderItemViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_manage_time_header, parent, false);
        return new HeaderVH(v);
    }

    @Override
    @NonNull
    public FooterVH onCreateFooterItemViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_manage_time_header, parent, false);
        ;
        return new FooterVH(v);
    }

    @Override
    public void onBindHeaderItemViewHolder(@NonNull HeaderVH holder, int localPosition) {
        holder.headerTv.setText("header " + localPosition);
    }

    @Override
    public void onBindFooterItemViewHolder(@NonNull FooterVH holder, int localPosition) {
        // bind data to footer items views
    }

    static class HeaderVH extends RecyclerView.ViewHolder {

        private MyTextView headerTv;

        public HeaderVH(@NonNull View itemView) {
            super(itemView);
            headerTv = itemView.findViewById(R.id.rcv_manage_time_header_day);
        }
    }

    static class FooterVH extends RecyclerView.ViewHolder {

        public FooterVH(@NonNull View itemView) {
            super(itemView);
        }
    }


}