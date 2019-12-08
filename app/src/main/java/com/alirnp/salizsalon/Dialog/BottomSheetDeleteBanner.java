package com.alirnp.salizsalon.Dialog;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alirnp.salizsalon.CustomViews.MyTextView;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.NestedJson.Result;
import com.alirnp.salizsalon.NestedJson.SalizResponse;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;
import com.alirnp.salizsalon.Utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BottomSheetDeleteBanner extends BottomSheetDialogFragment implements View.OnClickListener {

    private MyTextView textView;
    private View view;
    private int id;
    private Context context;
    private Callback<SalizResponse> callback = new Callback<SalizResponse>() {
        @Override
        public void onResponse(Call<SalizResponse> call, Response<SalizResponse> response) {
            String msg;

            if (response.isSuccessful()) {
                if (response.body() != null) {
                    Result result = response.body().getResult().get(0);
                    boolean success = Boolean.valueOf(result.getSuccess());
                    if (success) {
                        msg = context.getString(R.string.successfully);
                        Utils.sendMessageBannerChanged(context);
                    } else {
                        msg = result.getMessage();
                    }
                } else {
                    msg = context.getString(R.string.error_empty_body);
                }

            } else {
                msg = response.message();
            }

            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            dismiss();
        }

        @Override
        public void onFailure(Call<SalizResponse> call, Throwable t) {
            Toast.makeText(getContext(), t.getCause().getMessage(), Toast.LENGTH_LONG).show();
            dismiss();
        }
    };

    public BottomSheetDeleteBanner() {

    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
        initViews();
        return view;
    }

    private void initViews() {
        textView = view.findViewById(R.id.fragment_bottom_sheet_textView);
        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fragment_bottom_sheet_textView) {
            deleteBanner();
        }
    }

    private void deleteBanner() {
        Map<String, String> map = new HashMap<>();
        map.put(Constants.ID, String.valueOf(id));
        MyApplication.getApi().manage(Constants.DELETE_BANNER, Constants.TOKEN, map).enqueue(callback);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
