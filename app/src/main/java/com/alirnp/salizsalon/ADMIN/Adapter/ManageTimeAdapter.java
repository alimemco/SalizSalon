package com.alirnp.salizsalon.ADMIN.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.ADMIN.Model.Model;
import com.alirnp.salizsalon.CustomViews.MyTextView;
import com.alirnp.salizsalon.Dialog.EditTimeDialog;
import com.alirnp.salizsalon.Holder.NotFoundHolder;
import com.alirnp.salizsalon.Holder.SearchingHolder;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;
import com.alirnp.salizsalon.Utils.Utils;
import com.kodmap.library.kmrecyclerviewstickyheader.KmStickyListener;

import java.util.List;

public class ManageTimeAdapter extends
        ListAdapter<Model, RecyclerView.ViewHolder> implements
        KmStickyListener {


    public static final DiffUtil.ItemCallback<Model> ModelDiffUtilCallback =
            new DiffUtil.ItemCallback<Model>() {
                @Override
                public boolean areItemsTheSame(@NonNull Model model, @NonNull Model t1) {
                    return model.getType().equals(t1.getType());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Model model, @NonNull Model t1) {
                    return model.equals(t1);
                }
            };

    private Constants.state state;
    private EditTimeDialog dialog;

    public ManageTimeAdapter() {
        super(ModelDiffUtilCallback);
        state = Constants.state.SEARCHING;
    }

    @Override
    public void submitList(@Nullable List<Model> list) {
        state = Constants.state.SUCCESS;
        super.submitList(list);

    }

    public void setSearching() {
        setState(Constants.state.SEARCHING);

    }

    public void setNotFound() {
        setState(Constants.state.ITEM_NOT_FOUND);
    }

    private void setState(Constants.state state) {
        this.state = state;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View itemView;


        if (viewType == Constants.state.HEADER.getStatus()) {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_header, viewGroup, false);
            return new HeaderViewHolder(itemView);

        } else if (viewType == Constants.state.MAIN.getStatus()) {

            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_post, viewGroup, false);
            return new PostViewHolder(itemView);

        } else if (viewType == Constants.state.ITEM_NOT_FOUND.getStatus()) {
            return new NotFoundHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rcv_not_found, viewGroup, false));

        } else {
            return new SearchingHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rcv_searching, viewGroup, false));

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == Constants.state.HEADER.getStatus()) {
            ((HeaderViewHolder) viewHolder).bind(getItem(i));
        } else if (getItemViewType(i) == Constants.state.MAIN.getStatus()) {
            ((PostViewHolder) viewHolder).bind(getItem(i), i);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (state == Constants.state.SUCCESS) {
            return getItem(position).getType().getStatus();

        } else {
            return state.getStatus();
        }
    }

    @Override
    public Integer getHeaderPositionForItem(Integer itemPosition) {
        Integer headerPosition = 0;
        for (Integer i = itemPosition; i > 0; i--) {
            if (isHeader(i)) {
                headerPosition = i;
                return headerPosition;
            }
        }
        return headerPosition;
    }

    @Override
    public Integer getHeaderLayout(Integer headerPosition) {
        return R.layout.item_header;
    }

    @Override
    public void bindHeaderData(View header, Integer headerPosition) {
        TextView tv = header.findViewById(R.id.title_header);
        tv.setText(getItem(headerPosition).getDay());
    }

    @Override
    public Boolean isHeader(Integer itemPosition) {
        return getItem(itemPosition).getType().equals(Constants.state.HEADER);
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public MyTextView title;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_header);
        }

        public void bind(Model model) {
            title.setText(Utils.convertDayNameToPersian(model.getDay()));
        }
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        private MyTextView day, hour;
        private ImageView reservedImg;
        private View line;

        private PostViewHolder(@NonNull final View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.item_post_day);
            hour = itemView.findViewById(R.id.item_post_hour);
            line = itemView.findViewById(R.id.item_post_line);
            reservedImg = itemView.findViewById(R.id.item_post_img_reserved);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager manager = ((AppCompatActivity) view.getContext()).getSupportFragmentManager();

                    Model model = getItem(getAdapterPosition());
                    dialog = EditTimeDialog.newInstance(model.getId(), model.getHour(), model.isReserved());

                    dialog.show(manager, "EditTimeDialog");
                }
            });
        }

        public void bind(Model model, int position) {
            line.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
            day.setText(Utils.convertDayNameToPersian(model.getDay()));
            hour.setText(hourToAmPm(model.getHour()));
            reservedImg.setVisibility((model.isReserved()) ? View.VISIBLE : View.INVISIBLE);
        }

        private String hourToAmPm(String hour) {

            String[] split = hour.split(":");

            try {
                int h = Integer.valueOf(split[0]);
                if (h <= 12) {
                    return hour + " صبح ";
                } else {
                    return hour + " عصر ";
                }
            } catch (Exception e) {
                return "خطا در تجزیه اطلاعات";
            }

        }


    }
}