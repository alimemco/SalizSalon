package com.alirnp.salizsalon.Dialog;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alirnp.salizsalon.CustomViews.MyButton;
import com.alirnp.salizsalon.CustomViews.MyEditText;
import com.alirnp.salizsalon.NestedJson.Result;
import com.alirnp.salizsalon.NestedJson.SalizResponse;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;
import com.alirnp.salizsalon.Utils.MyApplication;
import com.alirnp.salizsalon.Utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetAddBanner extends BottomSheetDialogFragment
        implements View.OnClickListener {

    private View view;
    private Context context;
    private MyEditText linkEdiText;
    private MyButton submitButton;
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


    public BottomSheetAddBanner() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bottom_sheet_add_banner, container, false);
        initViews();
        return view;
    }

    private void initViews() {
        linkEdiText = view.findViewById(R.id.fragment_bottom_sheet_add_banner_link);
        submitButton = view.findViewById(R.id.fragment_bottom_sheet_add_banner_button);

        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fragment_bottom_sheet_add_banner_button) {


            if (linkEdiText.getText() != null) {
                String url = linkEdiText.getText().toString();
                if (URLUtil.isValidUrl(url)) {
                    addBanner(url);
                } else {
                    Toast.makeText(context, " آدرس صحیح وارد کنید ", Toast.LENGTH_SHORT).show();
                }
            }


        }
    }

    private void addBanner(String url) {
        Map<String, String> map = new HashMap<>();
        map.put(Constants.URL, url);
        MyApplication.getApi().manage(Constants.ADD_BANNER, Constants.TOKEN, map).enqueue(callback);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
