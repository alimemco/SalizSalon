package com.alirnp.salizsalon.Views.Activities;

import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alirnp.salizsalon.CustomViews.MyButton;
import com.alirnp.salizsalon.Interface.OnClickNext;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Views.Fragments.FragmentStepOne;
import com.alirnp.salizsalon.Views.Fragments.FragmentStepTwo;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;

public class ActivityChooseTime extends AppCompatActivity implements
        StepView.OnStepClickListener,
        View.OnClickListener ,
        OnClickNext {

    private StepView stepView;
    private MyButton nextStepBtn;

    private FragmentManager fragmentManager = getSupportFragmentManager();

    private FragmentStepOne fragmentStepOne = new FragmentStepOne(this);
    private FragmentStepTwo fragmentStepTwo = FragmentStepTwo.newInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time);

        initViews();
        // showDataPicker();

        replace(fragmentStepOne);


    }

    private void initFragments() {
    }

    private void initViews() {

        stepView = findViewById(R.id.activity_choose_time_step);
        nextStepBtn = findViewById(R.id.activity_choose_time_btn);


        stepView.getState()
                .animationType(StepView.ANIMATION_CIRCLE)
                .steps(new ArrayList<String>() {{
                    add("First step");
                    add("Second step");
                    add("Third step");
                }})
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .typeface(MyApplication.getIranSans(this))
                .commit();

        stepView.setOnStepClickListener(this);

    }

    private void replace(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.activity_choose_time_frame, fragment)
                .commit();
    }


    @Override
    public void onStepClick(int step) {
        stepView.go(step, true);
        switch (step) {
            case 0:
                replace(fragmentStepOne);

                break;

            case 1:
                replace(fragmentStepTwo);
                break;

            case 2:
                replace(fragmentStepOne);
                break;


        }

    }

    @Override
    public void onClick(View v) {
       if (v.getId() == R.id.activity_choose_time_btn){
           stepView.go(2,true);
           replace(fragmentStepTwo);
       }
    }

    @Override
    public void OnNext() {
        Toast.makeText(this, "Next In Activity", Toast.LENGTH_SHORT).show();
    }


}
