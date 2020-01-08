package com.alirnp.salizsalon.Dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.alirnp.salizsalon.CustomViews.MyButton;
import com.alirnp.salizsalon.CustomViews.MyEditText;
import com.alirnp.salizsalon.NestedJson.Result;
import com.alirnp.salizsalon.NestedJson.SalizResponse;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;
import com.alirnp.salizsalon.Utils.MyApplication;
import com.alirnp.salizsalon.Utils.Utils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTimeDialog extends DialogFragment implements View.OnClickListener {

    private View view;
    private int id;
    private String hour;
    private boolean reserved;

    private MyEditText hourEdt;
    private CheckBox reservedChb;
    private Callback<SalizResponse> callbackEditTime = new Callback<SalizResponse>() {
        @Override
        public void onResponse(Call<SalizResponse> call, Response<SalizResponse> response) {
            if (response.isSuccessful())
                if (response.body() != null) {
                    Result result = response.body().getResult().get(0);
                    boolean success = Boolean.parseBoolean(result.getSuccess());
                    if (success) {
                        Toast.makeText(getContext(), "با موفقیت تغییر کرد", Toast.LENGTH_SHORT).show();
                        Utils.sendMessageEditedTime(getContext());
                        dismiss();
                    } else {
                        Toast.makeText(getContext(), "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        }

        @Override
        public void onFailure(Call<SalizResponse> call, Throwable t) {
            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
    private Callback<SalizResponse> callbackDeleteTime = new Callback<SalizResponse>() {
        @Override
        public void onResponse(Call<SalizResponse> call, Response<SalizResponse> response) {
            if (response.isSuccessful()) {
                if (response.body() != null) {
                    Result result = response.body().getResult().get(0);
                    boolean success = Boolean.parseBoolean(result.getSuccess());
                    if (success) {
                        Toast.makeText(getContext(), "با موفقیت حذف شد", Toast.LENGTH_SHORT).show();
                        Utils.sendMessageEditedTime(getContext());
                        dismiss();
                    } else {
                        Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "خطا در سمت سرور", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFailure(Call<SalizResponse> call, Throwable t) {
            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    public static EditTimeDialog newInstance(int id, String hour, boolean reserved) {

        Bundle args = new Bundle();
        args.putInt(Constants.ID, id);
        args.putString(Constants.HOUR, hour);
        args.putBoolean(Constants.RESERVED, reserved);
        EditTimeDialog fragment = new EditTimeDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(Constants.ID);
            hour = getArguments().getString(Constants.HOUR);
            reserved = getArguments().getBoolean(Constants.RESERVED);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_manage_edit_time, container, false);

        if (getDialog() != null)
            if (getDialog().getWindow() != null) {
                getDialog().getWindow().setBackgroundDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.bg_dialog_rounded));
                getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            }
        initViews();
        return view;
    }

    private void initViews() {
        MyButton submitButton = view.findViewById(R.id.dialog_manage_edit_time_btn_submit);
        MyButton deleteButton = view.findViewById(R.id.dialog_manage_edit_time_btn_delete);
        hourEdt = view.findViewById(R.id.dialog_manage_edit_time_tv_hour);
        reservedChb = view.findViewById(R.id.dialog_manage_edit_time_chb_reserved);

        reservedChb.setTypeface(MyApplication.getIranSans(getContext()));

        hourEdt.setText(hour);
        reservedChb.setChecked(reserved);

        submitButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_manage_edit_time_btn_submit) {
            editInfo();
        } else if (v.getId() == R.id.dialog_manage_edit_time_btn_delete) {
            deleteInfo();
        }

    }


    private void editInfo() {
        Map<String, String> map = new HashMap<>();
        if (hourEdt.getText() != null)
            map.put(Constants.HOUR, hourEdt.getText().toString());
        map.put(Constants.ID, String.valueOf(id));
        map.put(Constants.RESERVED, String.valueOf(reservedChb.isChecked()));

        MyApplication.getApi().manage(Constants.EDIT_TIME, Constants.TOKEN, map).enqueue(callbackEditTime);

    }

    private void deleteInfo() {
        Map<String, String> map = new HashMap<>();
        map.put(Constants.ID, String.valueOf(id));
        MyApplication.getApi().manage(Constants.DELETE_TIME, Constants.TOKEN, map).enqueue(callbackDeleteTime);
    }


}
