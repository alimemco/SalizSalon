package com.alirnp.salizsalon.Adapters;


import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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

            statusTv.setText(getStatus(item));
            priceTv.setText(Utils.numberToTextPrice(item.getPrice()));
            hourTv.setText(item.getHour());

            String date = item.getDayName() + " " + item.getDayOfMonth() + " " + item.getMonthName();
            dateTv.setText(date);

            servicesTv.setText(SplitServices(item.getServices()));
            statusView.setBackground(getDrawableFromStatus(item));

        }

        private String SplitServices(String services) {
            StringBuilder sb = new StringBuilder();
            String[] split = services.split(",");

            for (String service : split) {
                sb.append(" *  ").append(service).append("\n");
            }
            return sb.toString();
        }

        private String getStatus(Item item) {

            if (item.getStatus().equals(Constants.statusReserve.PENDING.getStatus())) {
                return itemView.getResources().getString(R.string.status_pending);

            } else if (item.getStatus().equals(Constants.statusReserve.DENIED.getStatus())) {
                return itemView.getResources().getString(R.string.status_denied);

            } else if (item.getStatus().equals(Constants.statusReserve.FINALIZED.getStatus())) {
                return itemView.getResources().getString(R.string.status_finalized);

            } else {
                return itemView.getResources().getString(R.string.status_done);

            }
        }

        private Drawable getDrawableFromStatus(Item item) {

            if (item.getStatus().equals(Constants.statusReserve.DENIED.getStatus())) {
                return ContextCompat.getDrawable(itemView.getContext(), R.drawable.bg_circle_red);

            } else if (item.getStatus().equals(Constants.statusReserve.FINALIZED.getStatus())) {
                return ContextCompat.getDrawable(itemView.getContext(), R.drawable.bg_circle_green);

            } else if (item.getStatus().equals(Constants.statusReserve.DONE.getStatus())) {
                return ContextCompat.getDrawable(itemView.getContext(), R.drawable.bg_circle_blue);

            } else {
                return ContextCompat.getDrawable(itemView.getContext(), R.drawable.bg_circle_gray);
            }
        }
    }

}

