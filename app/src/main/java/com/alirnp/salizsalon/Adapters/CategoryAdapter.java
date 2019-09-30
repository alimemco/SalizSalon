package com.alirnp.salizsalon.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.CustomViews.MyTextView;
import com.alirnp.salizsalon.NestedJson.Item;
import com.alirnp.salizsalon.R;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;

import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item> models;
    private Context context;


    public CategoryAdapter(List<Item> models) {
        this.models = models;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_category, parent, false);
        return new CategoryHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        CategoryHolder mHolder = (CategoryHolder) holder;
        mHolder.bind(models.get(position));

    }


    @Override
    public int getItemCount() {
        return models == null ? 0 : models.size();
    }

    public static class CategoryHolder extends RecyclerView.ViewHolder {

        AppCompatImageView imageView;
        MyTextView title;

        CategoryHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.rcv_category_img);
            title = itemView.findViewById(R.id.rcv_category_tv);


        }

        void bind(Item item) {

            title.setText(item.getName());

            GlideToVectorYou
                    .init()
                    .with(itemView.getContext())
                    // .setPlaceHolder(R.color.colorAccent, R.color.design_default_color_primary)
                    .load(Uri.parse(item.getImage()), imageView);
        }
    }


}

