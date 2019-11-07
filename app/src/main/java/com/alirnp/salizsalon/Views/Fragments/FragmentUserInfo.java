package com.alirnp.salizsalon.Views.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.alirnp.salizsalon.CustomViews.MyButton;
import com.alirnp.salizsalon.Interface.OnLogoutUser;
import com.alirnp.salizsalon.Model.User;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.R;


public class FragmentUserInfo extends Fragment implements View.OnClickListener {

    private View view;
    private TextView textView;
    private MyButton exitBtn;
    // private RecyclerView rcv;

    private OnLogoutUser onLogoutUser;

    public FragmentUserInfo(OnLogoutUser onLogoutUser) {
        this.onLogoutUser = onLogoutUser;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_user_info, container, false);
        initViews();

        initSharedPreferences();

        return view;
    }

    private void initSharedPreferences() {
        User user = MyApplication.getSharedPrefManager().getUser();
        if (user != null) {
            String firstName = user.getFirstName();
            //  String phone = user.getPhone();
            if (firstName != null
                /*&& phone != null*/) {
                textView.setText(firstName);
                //   MyApplication.getApi().getUserReserveList(Constants.USER_RESERVE_LIST, phone).enqueue(callback());
            }

        }
    }

    private void initViews() {
        textView = view.findViewById(R.id.fragment_user_info_txt);
        exitBtn = view.findViewById(R.id.fragment_user_info_btn_exit);
        /*
        rcv = view.findViewById(R.id.fragment_user_info_rcv);
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));*/

        exitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fragment_user_info_btn_exit) {

            if (onLogoutUser != null) {
                onLogoutUser.onLogout();
            }
        }

    }
/*
    private Callback<ResponseJson> callback() {
        return new Callback<ResponseJson>() {
            @Override
            public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                if (response.isSuccessful())
                    if (response.body() != null) {
                        ResultItems result = response.body().getResult().get(0);
                        boolean success = Boolean.parseBoolean(result.getSuccess());
                        if (success) {

                            UserReserveAdapter adapter = new UserReserveAdapter(result.getItems());
                            rcv.setAdapter(adapter);

                        } else {
                            String msg = result.getMessage();
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    }

            }

            @Override
            public void onFailure(Call<ResponseJson> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        };
    }
*/
}
