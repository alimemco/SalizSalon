package com.alirnp.salizsalon.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.CustomViews.MyTextView;
import com.alirnp.salizsalon.NestedJson.Item;
import com.alirnp.salizsalon.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ItemsHorizontalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Item> models;


    public ItemsHorizontalAdapter(List<Item> models) {
        this.models = models;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_items_hor, parent, false);
        return new ItemsHorizontalHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ItemsHorizontalHolder mHolder = (ItemsHorizontalHolder) holder;
        mHolder.bind(models.get(position));


    }


    @Override
    public int getItemCount() {
        return models == null ? 0 : models.size();
    }

    public static class ItemsHorizontalHolder extends RecyclerView.ViewHolder {

        ImageView img;
        MyTextView title;

        ItemsHorizontalHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.rcv_item_hor_img);
            title = itemView.findViewById(R.id.rcv_item_hor_text);
        }

        void bind(Item item) {

            title.setText(item.getTitle());
            Picasso.get()
                    .load(item.getImage())
                    .into(img);

        }
    }
}

