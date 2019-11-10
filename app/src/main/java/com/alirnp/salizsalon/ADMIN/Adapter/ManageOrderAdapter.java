package com.alirnp.salizsalon.ADMIN.Adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.CustomViews.MyTextView;
import com.alirnp.salizsalon.Holder.NotFoundHolder;
import com.alirnp.salizsalon.Holder.SearchingHolder;
import com.alirnp.salizsalon.NestedJson.Item;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;
import com.alirnp.salizsalon.Utils.Utils;

import java.util.List;


public class ManageOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item> models;
    private Constants.state state;


    public ManageOrderAdapter() {
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
            return new ManageOrderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_manage_orders, parent, false));

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

        if (holder instanceof ManageOrderHolder) {
            ManageOrderHolder mHolder = (ManageOrderHolder) holder;
            mHolder.bind(position);
        }


    }


    @Override
    public int getItemCount() {
        return models == null ? 1 : models.size();
    }

    public class ManageOrderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private MyTextView nameTv, priceTv, phoneTv, dateTv, hourTv, servicesTv, statusTv;
        private ConstraintLayout constraintLayout;

        ManageOrderHolder(View itemView) {
            super(itemView);

            nameTv = itemView.findViewById(R.id.rcv_manage_order_name);
            priceTv = itemView.findViewById(R.id.rcv_manage_order_price);
            phoneTv = itemView.findViewById(R.id.rcv_manage_order_phone);
            dateTv = itemView.findViewById(R.id.rcv_manage_order_date);
            hourTv = itemView.findViewById(R.id.rcv_manage_order_hour);
            servicesTv = itemView.findViewById(R.id.rcv_manage_order_services);
            statusTv = itemView.findViewById(R.id.rcv_manage_order_status);

            constraintLayout = itemView.findViewById(R.id.rcv_manage_order_constraintHeader);
            constraintLayout.setOnClickListener(this);
        }

        void bind(int position) {
            Item item = models.get(position);

            String name = item.getFirst_name() + " " + item.getLast_name();
            String date = item.getDayName() + " " + item.getDayOfMonth() + " " + item.getMonthName();
            nameTv.setText(name);
            dateTv.setText(date);

            phoneTv.setText(item.getPhone());
            hourTv.setText(item.getHour());
            servicesTv.setText(Utils.splitServices(item.getServices()));
            statusTv.setText(Utils.getStatus(itemView.getContext(), item));
            priceTv.setText(Utils.numberToTextPrice(item.getPrice()));
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.rcv_manage_order_constraintHeader) {
                String phone = models.get(getAdapterPosition()).getPhone();

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                v.getContext().startActivity(intent);
            }
        }
    }
}

