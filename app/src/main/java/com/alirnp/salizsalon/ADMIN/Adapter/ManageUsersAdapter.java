package com.alirnp.salizsalon.ADMIN.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.CustomViews.MyTextView;
import com.alirnp.salizsalon.NestedJson.Item;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Utils;


public class ManageUsersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item> models;

    public ManageUsersAdapter() {
    }

    public void setResult(List<Item> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_manage_users, parent, false);
        return new ManageUsersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ManageUsersHolder mHolder = (ManageUsersHolder) holder;
        mHolder.bind(position);
    }

    public List<Item> getModels() {
        return models;
    }

    @Override
    public int getItemCount() {
        return models == null ? 0 : models.size();
    }


    class ManageUsersHolder extends RecyclerView.ViewHolder {
        private View lineView;
        private MyTextView usernameTextView, userLevelTextView;
        private ImageView kingImageView;

        private ManageUsersHolder(View itemView) {
            super(itemView);
            lineView = itemView.findViewById(R.id.rcv_manage_users_line);
            usernameTextView = itemView.findViewById(R.id.rcv_manage_users_username);
            userLevelTextView = itemView.findViewById(R.id.rcv_manage_users_userLevel);
            kingImageView = itemView.findViewById(R.id.rcv_manage_users_king);
        }

        void bind(final int position) {
            Item item = models.get(position);
            String level = item.getLevel();
            boolean isAdmin = level.equals("ADMIN");

            lineView.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
            kingImageView.setVisibility(isAdmin ? View.VISIBLE : View.INVISIBLE);

            usernameTextView.setText(item.getUsername());
            userLevelTextView.setText(Utils.parseUserLevel(level));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), models.get(getAdapterPosition()).getUsername(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
