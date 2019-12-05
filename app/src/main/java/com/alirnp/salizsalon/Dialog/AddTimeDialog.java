package com.alirnp.salizsalon.Dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.Adapters.DaysSelectAdapter;
import com.alirnp.salizsalon.CustomViews.MyButton;
import com.alirnp.salizsalon.CustomViews.MyEditText;
import com.alirnp.salizsalon.Generator.DataGenerator;
import com.alirnp.salizsalon.Model.Day;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.NestedJson.Result;
import com.alirnp.salizsalon.NestedJson.SalizResponse;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTimeDialog extends DialogFragment implements
        View.OnClickListener,
        DaysSelectAdapter.OnDayClick {

    private View view;

    private RecyclerView recyclerView;
    private MyEditText hourEditText;
    private MyButton verifyButton;

    private LoadingDialog dialog = new LoadingDialog();

    private OnDoneProgress onDoneProgress;
    private Day day;
    private Callback<SalizResponse> callback = new Callback<SalizResponse>() {
        @Override
        public void onResponse(Call<SalizResponse> call, Response<SalizResponse> response) {
            dialog.dismiss();
            if (response.isSuccessful()) {
                if (response.body() != null) {
                    Result result = response.body().getResult().get(0);
                    boolean success = Boolean.parseBoolean(result.getSuccess());
                    if (success) {
                        Toast.makeText(getContext(), "با موفقیت اضافه شد", Toast.LENGTH_SHORT).show();
                        dismiss();
                        if (onDoneProgress != null)
                            onDoneProgress.OnDone();
                    } else {
                        Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(getContext(), "خطا در سرور", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<SalizResponse> call, Throwable t) {
            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_time, container, false);
        initViews();

        if (getDialog() != null) {
            if (getDialog().getWindow() != null) {
                getDialog().getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_rounded);
                getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            }
        }

        return view;
    }

    private void initViews() {
        hourEditText = view.findViewById(R.id.dialog_add_time_edt_hour);
        verifyButton = view.findViewById(R.id.dialog_add_time_btn_verify);
        recyclerView = view.findViewById(R.id.dialog_add_time_rcv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        DaysSelectAdapter adapter = new DaysSelectAdapter(DataGenerator.getWeekDays());
        adapter.setOnDayClick(this);
        recyclerView.setAdapter(adapter);

        verifyButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.dialog_add_time_btn_verify) {
            if (day != null) {
                Map<String, String> map = new HashMap<>();
                if (hourEditText.getText() != null)
                    map.put(Constants.HOUR, hourEditText.getText().toString());
                map.put(Constants.DAY_OF_WEEK, String.valueOf(day.getDayOfWeek()));

                MyApplication.getApi().manage(Constants.ADD_TIME, Constants.TOKEN, map).enqueue(callback);
                showLoading();

            } else {
                Toast.makeText(getContext(), "روز را مشخص کنید", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void showLoading() {
        if (getFragmentManager() != null)
            dialog.show(getFragmentManager(), "LoadingDialog");
    }

    public void setOnDoneProgress(OnDoneProgress onDoneProgress) {
        this.onDoneProgress = onDoneProgress;
    }

    @Override
    public void OnDay(Day day) {
        this.day = day;
    }


    public interface OnDoneProgress {
        void OnDone();
    }
}
