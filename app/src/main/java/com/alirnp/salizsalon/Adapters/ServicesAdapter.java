package com.alirnp.salizsalon.Adapters;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.CustomViews.MyTextView;
import com.alirnp.salizsalon.Model.Service;
import com.alirnp.salizsalon.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Service> models;
    private SparseBooleanArray booleanArray = new SparseBooleanArray();
    private onServiceSelect onServiceSelect;

    public ServicesAdapter(ArrayList<Service> models) {
        this.models = models;
    }

    public void setOnServiceSelect(ServicesAdapter.onServiceSelect onServiceSelect) {
        this.onServiceSelect = onServiceSelect;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_service, parent, false);
        return new ServiceHolder(view, onServiceSelect);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ServiceHolder mHolder = (ServiceHolder) holder;
        mHolder.bind(position);
    }


    @Override
    public int getItemCount() {
        return models == null ? 0 : models.size();
    }

    private List<Service> applyFilter(List<Service> models) {
        List<Service> list = new ArrayList<>();
        for (Service service : models) {
            if (service.isChecked())
                list.add(service);
        }
        return list;
    }

    public interface onServiceSelect {
        void services(List<Service> model);
    }

    public class ServiceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private MyTextView title;
        private MyTextView price;
        private CheckBox chb;
        private ConstraintLayout root;


        ServiceHolder(View itemView, ServicesAdapter.onServiceSelect onServiceSelect) {
            super(itemView);

            title = itemView.findViewById(R.id.rcv_service_txt_title);
            price = itemView.findViewById(R.id.rcv_service_txt_price);
            chb = itemView.findViewById(R.id.rcv_service_chb);

            root = itemView.findViewById(R.id.rcv_service_root);

            itemView.setOnClickListener(this);
            title.setOnClickListener(this);
            price.setOnClickListener(this);
            root.setOnClickListener(this);

        }

        void bind(final int position) {

            final Service service = models.get(position);

            title.setText(service.getName());

            NumberFormat formatter = new DecimalFormat("#,###,###,###");
            String value = formatter.format(service.getPrice()) + " تومان ";
            price.setText(value);

            if (!booleanArray.get(position, false)) {
                notify(position, false, false);
            } else {
                notify(position, false, true);
            }


        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();

            if (!booleanArray.get(adapterPosition, false)) {
                notify(adapterPosition, true, true);
            } else {
                notify(adapterPosition, true, false);
            }
        }


        private void notify(int position, boolean clicked, boolean checked) {
            models.get(position).setChecked(checked);
            chb.setChecked(checked);
            if (clicked) {
                booleanArray.put(position, checked);

                if (onServiceSelect != null)
                    onServiceSelect.services(applyFilter(models));
            }
        }
    }


}

