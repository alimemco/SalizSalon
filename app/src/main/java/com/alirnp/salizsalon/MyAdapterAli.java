package com.alirnp.salizsalon;


import android.view.View;

public class MyAdapterAli extends RecyclerAli.Adapter<MyAdapterAli.MyHolder> {

    @Override
    public MyHolder onCreateViewHolder(View parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyHolder viewHolder, int position) {

    }

    @Override
    public int getItemCount() {
        return 12;
    }

    public class MyHolder extends RecyclerAli.ViewHolder {

        public MyHolder(View itemView) {
            super(itemView);
        }
    }

}
