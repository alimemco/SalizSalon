package com.alirnp.salizsalon.ADMIN.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.Holder.NotFoundHolder;
import com.alirnp.salizsalon.Holder.SearchingHolder;
import com.alirnp.salizsalon.NestedJson.Item;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ManageBannersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item> models;
    private Constants.state state;


    public ManageBannersAdapter() {
        state = Constants.state.SEARCHING;
        notifyDataSetChanged();
    }


    private void setState(Constants.state state) {
        this.state = state;
        notifyDataSetChanged();
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

    public void setData(List<Item> models) {
        this.models = models;
        setState(Constants.state.SUCCESS);
    }

    public void setSearching() {
        setState(Constants.state.SEARCHING);

    }

    public void setNotFound() {
        setState(Constants.state.ITEM_NOT_FOUND);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        if (viewType == Constants.state.SUCCESS.getStatus()) {
            return new ManageBannersHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_manage_banners, parent, false));

        } else if (viewType == Constants.state.ITEM_NOT_FOUND.getStatus()) {
            return new NotFoundHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_not_found, parent, false));

        } else {
            return new SearchingHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_searching, parent, false));
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ManageBannersHolder) {
            ManageBannersHolder mHolder = (ManageBannersHolder) holder;
            mHolder.bind(position);
        }


    }


    @Override
    public int getItemCount() {
        return models == null ? 1 : models.size();
    }

    public class ManageBannersHolder extends RecyclerView.ViewHolder {

        private ImageView image;

        ManageBannersHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.rcv_manage_banners_img);
        }

        void bind(final int position) {
            Item item = models.get(position);

            Picasso.get()
                    .load(item.getUrl())
                    .into(image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "" + position, Toast.LENGTH_SHORT).show();
                }
            });

        }


    }
}

