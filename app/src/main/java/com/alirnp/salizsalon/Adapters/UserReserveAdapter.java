package com.alirnp.salizsalon.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.CustomViews.MyTextView;
import com.alirnp.salizsalon.Holder.NotFoundHolder;
import com.alirnp.salizsalon.Holder.SearchingHolder;
import com.alirnp.salizsalon.NestedJson.Item;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;
import com.alirnp.salizsalon.Utils.Utils;

import java.util.List;


public class UserReserveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item> models;
    private Constants.state state;

    public UserReserveAdapter() {
        setState(Constants.state.SEARCHING);
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
            return new UserReserveHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_user_reserve, parent, false));

        } else if (viewType == Constants.state.ITEM_NOT_FOUND.getStatus()) {
            return new NotFoundHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_not_found, parent, false));

        } else if (viewType == Constants.state.SEARCHING.getStatus()) {
            return new SearchingHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_searching, parent, false));

        } else {
            return new SearchingHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_searching, parent, false));
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserReserveHolder) {
            UserReserveHolder mHolder = (UserReserveHolder) holder;
            mHolder.bind(models.get(position));
        }
    }


    @Override
    public int getItemCount() {
        return models == null ? 1 : models.size();
    }

    public class UserReserveHolder extends RecyclerView.ViewHolder {

        private MyTextView dateTv, hourTv, servicesTv, statusTv, priceTv;
        private View statusView;

        UserReserveHolder(View itemView) {
            super(itemView);

            statusTv = itemView.findViewById(R.id.rcv_user_reserve_status);
            priceTv = itemView.findViewById(R.id.rcv_user_reserve_price);
            dateTv = itemView.findViewById(R.id.rcv_user_reserve_date);
            hourTv = itemView.findViewById(R.id.rcv_user_reserve_hour);
            servicesTv = itemView.findViewById(R.id.rcv_user_reserve_services);

            statusView = itemView.findViewById(R.id.rcv_user_reserve_statusImage);

        }

        void bind(Item item) {

            statusTv.setText(Utils.getStatus(itemView.getContext(), item));
            priceTv.setText(Utils.numberToTextPrice(item.getPrice(), true));
            hourTv.setText(item.getHour());

            String date = item.getDayName() + " " + item.getDayOfMonth() + " " + item.getMonthName();
            dateTv.setText(date);

            servicesTv.setText(Utils.splitServices(item.getServices()));
            statusTv.setTextColor(Utils.getStatusColor(itemView.getContext(), item));
            statusView.setBackground(Utils.getDrawableFromStatus(itemView.getContext(), item));

        }

    }

}

