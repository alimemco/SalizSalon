package com.alirnp.salizsalon.Dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alirnp.salizsalon.NestedJson.Result;
import com.alirnp.salizsalon.NestedJson.SalizResponse;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;
import com.alirnp.salizsalon.Utils.MyApplication;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetChangeUserLevel extends BottomSheetDialogFragment
        implements View.OnClickListener {

    private View view;
    private int id;
    private int position;
    private String userLevel = "NEW_COMER";
    private OnUserLevelChanged onUserLevelChanged;
    private Callback<SalizResponse> callback = new Callback<SalizResponse>() {
        @Override
        public void onResponse(Call<SalizResponse> call, Response<SalizResponse> response) {
            dismiss();
            String error = "";

            if (response.isSuccessful()) {
                if (response.body() != null) {
                    Result result = response.body().getResult().get(0);
                    boolean success = Boolean.parseBoolean(result.getSuccess());
                    if (success) {
                        if (getContext() != null) {
                            Toast.makeText(getContext(), getContext().getString(R.string.successfully), Toast.LENGTH_SHORT).show();
                            if (onUserLevelChanged != null)
                                onUserLevelChanged.OnUserChanged(position, userLevel);
                            return;
                        }


                    } else {
                        error = result.getMessage();
                    }
                } else {
                    if (getContext() != null)
                        error = getContext().getString(R.string.error_empty_body);
                }
            } else {
                error = response.message();
            }
            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onFailure(Call<SalizResponse> call, Throwable t) {
            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            dismiss();
        }
    };

    public BottomSheetChangeUserLevel() {

    }

    public static BottomSheetChangeUserLevel newInstance(int id, int position) {

        Bundle args = new Bundle();
        args.putInt(Constants.ID, id);
        args.putInt(Constants.POSITION, position);
        BottomSheetChangeUserLevel fragment = new BottomSheetChangeUserLevel();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(Constants.ID);
            position = getArguments().getInt(Constants.POSITION);

        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bottom_sheet_change_user_level, container, false);
        initViews();
        return view;
    }

    private void initViews() {
        LinearLayout upLinearLayout = view.findViewById(R.id.fragment_bottom_sheet_change_user_level_up);
        LinearLayout downLinearLayout = view.findViewById(R.id.fragment_bottom_sheet_change_user_level_down);

        upLinearLayout.setOnClickListener(this);
        downLinearLayout.setOnClickListener(this);

    }

    private void editUserLevel(int id, String userLevel) {
        this.userLevel = userLevel;

        Map<String, String> map = new HashMap<>();
        map.put(Constants.ID, String.valueOf(id));
        map.put(Constants.USER_LEVEL, String.valueOf(userLevel));
        MyApplication.getApi().manage(Constants.EDIT_USER_LEVEL, Constants.TOKEN, map).enqueue(callback);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_bottom_sheet_change_user_level_up:
                editUserLevel(id, Constants.user_level.ADMIN.getLevel());
                break;

            case R.id.fragment_bottom_sheet_change_user_level_down:
                editUserLevel(id, Constants.user_level.NEW_COMER.getLevel());
                break;
        }
    }

    public void setOnUserLevelChanged(OnUserLevelChanged onUserLevelChanged) {
        this.onUserLevelChanged = onUserLevelChanged;
    }

    public interface OnUserLevelChanged {
        void OnUserChanged(int position, String userLevel);
    }
}
