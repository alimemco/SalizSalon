package com.alirnp.salizsalon.Dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.Adapters.DaysSelectAdapter;
import com.alirnp.salizsalon.CustomViews.MyEditText;
import com.alirnp.salizsalon.Generator.DataGenerator;
import com.alirnp.salizsalon.R;

public class AddTimeDialog extends DialogFragment {

    private View view;

    private RecyclerView recyclerView;
    private MyEditText hourEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_time, container, false);
        initViews();
        return view;
    }

    private void initViews() {
        hourEditText = view.findViewById(R.id.dialog_add_time_edt_hour);
        recyclerView = view.findViewById(R.id.dialog_add_time_rcv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        DaysSelectAdapter adapter = new DaysSelectAdapter(DataGenerator.getWeekDays());
        recyclerView.setAdapter(adapter);


    }
}
