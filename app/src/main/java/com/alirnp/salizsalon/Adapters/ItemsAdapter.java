package com.alirnp.salizsalon.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.CustomViews.MyTextView;
import com.alirnp.salizsalon.Model.Item;
import com.alirnp.salizsalon.R;

import java.util.List;


public class ItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item> models;
    private OnItemClick onItemClick;

    public ItemsAdapter(List<Item> models) {
        this.models = models;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_items, parent, false);
        return new ItemsHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ItemsHolder mHolder = (ItemsHolder) holder;

        //bind(mHolder,models.get(position));
        mHolder.bind(models.get(position), onItemClick);

    }

    private void bind(ItemsHolder itemsHolder, final Item item) {

        itemsHolder.txt.setText(item.getTitle());

        itemsHolder.imageView.setImageResource(item.getImage());

      /*  itemsHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.log(ItemsAdapter.class,"man");
            }
        });

        itemsHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.log(ItemsAdapter.class,"click");
                if (onItemClick != null){
                    onItemClick.OnClick(item.getID());
                    Utils.log(ItemsAdapter.class,"click ok");
                }else {
                    Utils.log(ItemsAdapter.class,"click null");
                }
            }
        });*/


    }


    @Override
    public int getItemCount() {
        return models == null ? 0 : models.size();
    }

    public interface OnItemClick {
        void OnClick(int ID);
    }

    public static class ItemsHolder extends RecyclerView.ViewHolder {

        AppCompatImageView imageView;
        MyTextView txt;
        ConstraintLayout rootLayout;

        ItemsHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.rcv_items_img);
            txt = itemView.findViewById(R.id.rcv_items_txt);
            rootLayout = itemView.findViewById(R.id.rcv_items_root);
        }

        void bind(final Item item, final OnItemClick onItemClick) {

            txt.setText(item.getTitle());

            imageView.setImageResource(item.getImage());

            rootLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClick != null) {
                        onItemClick.OnClick(item.getID());
                    }
                }
            });


        }


    }
}
