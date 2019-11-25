package com.alirnp.salizsalon.ADMIN.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.CustomViews.MyTextView;
import com.alirnp.salizsalon.Expand.GroupHelper;
import com.alirnp.salizsalon.Expand.GroupList;
import com.alirnp.salizsalon.Holder.NotFoundHolder;
import com.alirnp.salizsalon.Holder.SearchingHolder;
import com.alirnp.salizsalon.NestedJson.Item;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;
import com.alirnp.salizsalon.Utils.Utils;

import java.util.List;


public class ManageTimesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item> models;
    private Constants.state state;

    private GroupHelper searchList;

    public ManageTimesAdapter() {
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
                //  return Constants.state.SUCCESS.getStatus();
                return searchList.getUnflattenedPosition(position).type;

            case ITEM_NOT_FOUND:
                return Constants.state.ITEM_NOT_FOUND.getStatus();

            case SEARCHING:
                return Constants.state.SEARCHING.getStatus();

            case HEADER:
                return Constants.state.HEADER.getStatus();

            case FOOTER:
                return Constants.state.FOOTER.getStatus();

            default:
                return Constants.state.SEARCHING.getStatus();
        }

    }

    public void setData(List<GroupList> models) {
        // this.models = models;
        this.searchList = new GroupHelper(models);
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


        if (viewType == Constants.state.MAIN.getStatus()) {
            return new ManageTimeHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_manage_times, parent, false));

        } else if (viewType == Constants.state.HEADER.getStatus()) {
            return new ManageTimeHeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_manage_time_header, parent, false));

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

        if (holder instanceof ManageTimeHolder) {
            ManageTimeHolder mHolder = (ManageTimeHolder) holder;
            mHolder.bind(position);
        } else if (holder instanceof ManageTimeHeaderHolder) {
            ManageTimeHeaderHolder mHolder = (ManageTimeHeaderHolder) holder;
            mHolder.bind(position);
        }

    }


    @Override
    public int getItemCount() {

        if (state == Constants.state.ITEM_NOT_FOUND || state == Constants.state.SEARCHING) {
            return 1;
        } else {
            return searchList == null ? 0 : searchList.getItemCount();
        }
    }

    public class ManageTimeHolder extends RecyclerView.ViewHolder {

        private MyTextView dayTv, hourTv;
        private SwitchCompat canReservedSw;

        ManageTimeHolder(View itemView) {
            super(itemView);

            //   dayTv = itemView.findViewById(R.id.rcv_manage_times_day);
            hourTv = itemView.findViewById(R.id.rcv_manage_times_hour);
            canReservedSw = itemView.findViewById(R.id.rcv_manage_times_canReserve);

        }

        void bind(int position) {
//            Item item = models.get(position);
            //  List<SubItem> subItems = item.getSubItem();

            //dayTv.setText(Utils.convertDayNameToPersian(week.get));
            hourTv.setText(position + " hour");
            canReservedSw.setChecked(position % 2 == 0);
        }


    }

    public class ManageTimeHeaderHolder extends RecyclerView.ViewHolder {

        private MyTextView dayTv;

        ManageTimeHeaderHolder(View itemView) {
            super(itemView);

            dayTv = itemView.findViewById(R.id.rcv_manage_time_header_day);

            //Week week = models.get(getAdapterPosition());


        }

        void bind(int position) {

            // models.get()
            // dayTv.setText(Utils.convertDayNameToPersian(models));
            dayTv.setText(position + " day ");

        }


    }
}

