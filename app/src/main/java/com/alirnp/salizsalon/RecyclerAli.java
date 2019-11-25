package com.alirnp.salizsalon;

import android.view.View;

public class RecyclerAli {

    public static abstract class Adapter<VH extends ViewHolder> {

        public abstract VH onCreateViewHolder(View parent, int viewType);

        public abstract void onBindViewHolder(VH viewHolder, int position);

        public abstract int getItemCount();

    }

    public static abstract class ViewHolder {
        View itemView;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
        }
    }
}
