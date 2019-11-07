package com.alirnp.salizsalon.Views.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.alirnp.salizsalon.CustomViews.MyButton;
import com.alirnp.salizsalon.Dialog.BottomSheetFragment;
import com.alirnp.salizsalon.Interface.OnStepReady;
import com.alirnp.salizsalon.Model.Day;
import com.alirnp.salizsalon.Model.Hour;
import com.alirnp.salizsalon.Model.JSON.Result;
import com.alirnp.salizsalon.Model.Service;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;
import com.alirnp.salizsalon.Utils.Utils;
import com.alirnp.salizsalon.Views.Fragments.FragmentStepOne;
import com.alirnp.salizsalon.Views.Fragments.FragmentStepThree;
import com.alirnp.salizsalon.Views.Fragments.FragmentStepTwo;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityChooseTime extends AppCompatActivity implements
        View.OnClickListener,
        OnStepReady {

    private static HashMap<Constants.data, Object> data = new HashMap<>();
    private Day day;
    private Hour hour;
    private ArrayList<Service> services;

    private StepView stepView;
    private MyButton nextStepBtn;

    private BottomSheetFragment bottomSheetFragment;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentStepOne fragmentStepOne = new FragmentStepOne(this);
    private FragmentStepTwo fragmentStepTwo = new FragmentStepTwo(this);
    private FragmentStepThree fragmentStepThree = new FragmentStepThree(this);

    public static void setValues(Constants.data key, Object object) {
        data.put(key, object);
    }

    public static HashMap<Constants.data, Object> getData() {
        return data;
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            runStepThree();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time);

        services = new ArrayList<>();

        initViews();

        replace(fragmentStepOne);

        initBroadcasts();
    }

    private void initViews() {

        stepView = findViewById(R.id.activity_choose_time_step);
        nextStepBtn = findViewById(R.id.activity_choose_time_btn);

        nextStepBtn.setOnClickListener(this);


        stepView.getState()
                .animationType(StepView.ANIMATION_CIRCLE)
                .steps(new ArrayList<String>() {{
                    add("time");
                    add("service");
                    add("final");
                }})
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .typeface(MyApplication.getIranSans(this))
                .commit();


    }

    private void showBottomDialog() {
        bottomSheetFragment = new BottomSheetFragment();
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());

    }

    private void initBroadcasts() {
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(Constants.EVENT_LOGIN));

    }

    private void replace(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.activity_choose_time_frame, fragment)
                .commit();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.activity_choose_time_btn) {

            int step = stepView.getCurrentStep();
            goToStep(step + 1);

        }
    }

    @Override
    public void OnReady(int step, boolean enabled) {
        nextStepBtn.setEnabled(enabled);
    }


    @Override
    public void onBackPressed() {
        int currentStep = stepView.getCurrentStep();

        if (currentStep == 0)
            super.onBackPressed();

        else {
            goToStep(currentStep - 1);
        }

    }

    private void goToStep(int step) {
        stepView.go(step, true);
        nextStepBtn.setEnabled(false);

        switch (step) {
            case 0:
                replace(fragmentStepOne);
                nextStepBtn.setText(R.string.choose_dayHour);
                break;

            case 1:

                replace(fragmentStepTwo);
                nextStepBtn.setText(R.string.choose_service);
                break;

            case 2:

                day = (Day) data.get(Constants.data.DAY);
                hour = (Hour) data.get(Constants.data.HOUR);
                services = (ArrayList<Service>) data.get(Constants.data.SERVICES);

                //TODO Change Generic java :)

                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.DAY, day);
                bundle.putParcelable(Constants.HOUR, hour);
                bundle.putParcelableArrayList(Constants.SERVICES, services);
                fragmentStepThree.setArguments(bundle);
                replace(fragmentStepThree);
                nextStepBtn.setText(R.string.choose_final);

                break;

            case 3:
                runStepThree();
                break;
        }
    }

    private void runStepThree() {

        String phone = MyApplication.getSharedPrefManager().getUser().getPhone();
        if (phone == null) {
            showBottomDialog();
        } else {
            Map<String, String> info = new HashMap<>();

            info.put(Constants.PHONE, phone);
            info.put(Constants.DAY_NAME, day.getDayName());
            info.put(Constants.MONTH_NAME, day.getMonthName());
            info.put(Constants.DAY_OF_MONTH, day.getDayOfMonth());
            info.put(Constants.HOUR, hour.getTime());
            info.put(Constants.PRICE, String.valueOf(calculatorPrice()));
            info.put(Constants.SERVICES, explodeServices());

            MyApplication.getApi().reserve(
                    Constants.RESERVE,
                    info
            ).enqueue(callback());
        }

        nextStepBtn.setEnabled(true);
    }

    private int calculatorPrice() {
        int price = 0;
        for (int i = 0; i < services.size(); i++) {
            price += services.get(i).getPrice();
        }
        return price;
    }

    private String explodeServices() {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < services.size(); i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(services.get(i).getName());
        }
        return sb.toString();
    }


    private Callback<ArrayList<Result>> callback() {
        return new Callback<ArrayList<Result>>() {
            @Override
            public void onResponse(Call<ArrayList<Result>> call, Response<ArrayList<Result>> response) {
                if (response.body() != null) {
                    Result result = response.body().get(0);
                    if (Boolean.valueOf(result.getSuccess())) {
                        Toast.makeText(ActivityChooseTime.this, "با موفقیت ارسال شد ، منتظر تایید باشید", Toast.LENGTH_LONG).show();
                        finish();
                        Utils.sendMessageReserved(ActivityChooseTime.this);

                    } else {
                        Toast.makeText(ActivityChooseTime.this, result.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Result>> call, Throwable t) {
                Toast.makeText(ActivityChooseTime.this, t.toString(), Toast.LENGTH_LONG).show();

            }
        };
    }


}
