package com.alirnp.salizsalon.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.CustomViews.MyTextView;
import com.alirnp.salizsalon.NestedJson.Item;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;
import com.alirnp.salizsalon.Utils.Utils;

import java.util.List;


public class UserReserveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Item> models;


    public UserReserveAdapter(List<Item> models) {
        this.models = models;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_user_reserve, parent, false);
        return new UserReserveHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        UserReserveHolder mHolder = (UserReserveHolder) holder;
        mHolder.bind(models.get(position));
    }


    @Override
    public int getItemCount() {
        return models == null ? 0 : models.size();
    }

    public class UserReserveHolder extends RecyclerView.ViewHolder {
        private MyTextView statusTv;
        private MyTextView priceTv;

        UserReserveHolder(View itemView) {
            super(itemView);

            statusTv = itemView.findViewById(R.id.rcv_user_reserve_status);
            priceTv = itemView.findViewById(R.id.rcv_user_reserve_price);

        }

        void bind(Item item) {
            String status = "";
            if (item.getStatus().equals(Constants.statusReserve.PENDING.getStatus())) {
                status = itemView.getResources().getString(R.string.status_pending);

            } else if (item.getStatus().equals(Constants.statusReserve.DENIED.getStatus())) {
                status = itemView.getResources().getString(R.string.status_denied);

            } else if (item.getStatus().equals(Constants.statusReserve.FINALIZED.getStatus())) {
                status = itemView.getResources().getString(R.string.status_finalized);

            } else if (item.getStatus().equals(Constants.statusReserve.DONE.getStatus())) {
                status = itemView.getResources().getString(R.string.status_done);

            }

            statusTv.setText(status);
            priceTv.setText(Utils.numberToTextPrice(Integer.valueOf(item.getPrice())));

        }
    }

}

