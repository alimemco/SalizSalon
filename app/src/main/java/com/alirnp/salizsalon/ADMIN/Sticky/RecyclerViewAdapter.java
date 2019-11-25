package com.alirnp.salizsalon.ADMIN.Sticky;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.R;
import com.kodmap.library.kmrecyclerviewstickyheader.KmStickyListener;

public class RecyclerViewAdapter extends ListAdapter<Model, RecyclerView.ViewHolder> implements KmStickyListener {

    public static final DiffUtil.ItemCallback<Model> ModelDiffUtilCallback =
            new DiffUtil.ItemCallback<Model>() {
                @Override
                public boolean areItemsTheSame(@NonNull Model model, @NonNull Model t1) {
                    return model.title.equals(t1.title);
                }

                @Override
                public boolean areContentsTheSame(@NonNull Model model, @NonNull Model t1) {
                    return model.equals(t1);
                }
            };

    public RecyclerViewAdapter() {
        super(ModelDiffUtilCallback);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView;
        if (viewType == ItemType.Header) {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_header, viewGroup, false);
            return new HeaderViewHolder(itemView);
        } else {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_post, viewGroup, false);
            return new PostViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == ItemType.Header) {
            ((HeaderViewHolder) viewHolder).bind(getItem(i));
        } else {
            ((PostViewHolder) viewHolder).bind(getItem(i));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).type;
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
        tv.setText(getItem(headerPosition).title);
    }

    @Override
    public Boolean isHeader(Integer itemPosition) {
        return getItem(itemPosition).type.equals(ItemType.Header);
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_header);
        }

        public void bind(Model model) {
            title.setText(model.title);
        }
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_post);
        }

        public void bind(Model model) {
            title.setText(model.title);
        }
    }
}