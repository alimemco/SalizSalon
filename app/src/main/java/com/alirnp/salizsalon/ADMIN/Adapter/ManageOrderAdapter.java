package com.alirnp.salizsalon.ADMIN.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.CustomViews.MyTextView;
import com.alirnp.salizsalon.NestedJson.Item;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Utils;

import java.util.List;


public class ManageOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item> models;


    public ManageOrderAdapter(List<Item> models) {
        this.models = models;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_manage_orders, parent, false);
        return new ManageOrderHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ManageOrderHolder mHolder = (ManageOrderHolder) holder;
        mHolder.bind(position);

    }


    @Override
    public int getItemCount() {
        return models == null ? 0 : models.size();
    }

    public  class ManageOrderHolder extends RecyclerView.ViewHolder {

        private MyTextView firstName, price;

        ManageOrderHolder(View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.rcv_manage_order_firstName);
            price = itemView.findViewById(R.id.rcv_manage_order_price);

        }

        void bind(int position) {
            Item item = models.get(position);

            String name = item.getFirst_name()+" "+item.getLast_name();
            firstName.setText(name);

            price.setText(Utils.numberToTextPrice(item.getPrice()));
        }
    }
}

