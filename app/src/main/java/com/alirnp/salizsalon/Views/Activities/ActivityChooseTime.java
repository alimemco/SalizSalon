package com.alirnp.salizsalon.Views.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alirnp.salizsalon.CustomViews.MyButton;
import com.alirnp.salizsalon.Interface.OnStepReady;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Views.Fragments.FragmentStepOne;
import com.alirnp.salizsalon.Views.Fragments.FragmentStepThree;
import com.alirnp.salizsalon.Views.Fragments.FragmentStepTwo;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;

public class ActivityChooseTime extends AppCompatActivity implements
        View.OnClickListener ,
        OnStepReady {

    private StepView stepView;
    private MyButton nextStepBtn;

    private FragmentManager fragmentManager = getSupportFragmentManager();

    private FragmentStepOne fragmentStepOne = new FragmentStepOne(this);
    private FragmentStepTwo fragmentStepTwo = FragmentStepTwo.newInstance();
    private FragmentStepThree fragmentStepThree = FragmentStepThree.newInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time);

        initViews();

        replace(fragmentStepOne);
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

    private void replace(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.activity_choose_time_frame, fragment)
                .commit();
    }


    @Override
    public void onClick(View v) {
       if (v.getId() == R.id.activity_choose_time_btn){

           int step = stepView.getCurrentStep();
           goToStep(step + 1);

       }
    }

    @Override
    public void OnReady(int step, boolean enabled) {
        switch (step) {
            case 1:
                nextStepBtn.setEnabled(enabled);
                break;

            case 2:

                break;
        }
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
                replace(fragmentStepThree);
                nextStepBtn.setText(R.string.choose_final);
                break;

            case 3:
                Toast.makeText(this, "Last Step", Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
