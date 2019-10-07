package com.alirnp.salizsalon.Views.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ir.he.meowdatetimepicker.MeowTypefaceHelper;
import ir.he.meowdatetimepicker.date.DatePickerDialog;
import ir.he.meowdatetimepicker.utils.PersianCalendar;


public class FragmentStepOne extends Fragment implements DatePickerDialog.OnDateSetListener {

    private View view;

    public FragmentStepOne() {
    }

    public static FragmentStepOne newInstance() {

        Bundle args = new Bundle();

        FragmentStepOne fragment = new FragmentStepOne();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_step_one, container, false);
        initViews();

        getOpenTimes();

        return view;
    }

    private void getOpenTimes() {


        // MyApplication.getApi().getTimes("times",getCurrentDay()).enqueue(this);


    }

    private String getCurrentDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());
        Date d = new Date();
        return sdf.format(d);
    }

    private void initViews() {
        AppCompatButton chooseBtn = view.findViewById(R.id.fragment_step_one_button);

        chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDataPicker();

            }
        });
    }

    private void showDataPicker() {


        MeowTypefaceHelper.init(MyApplication.getIranSans(getContext()),
                MyApplication.getIranSans(getContext()),
                MyApplication.getIranSansBold(getContext()),
                MyApplication.getIranSans(getContext()));

        PersianCalendar persianCalendar = new PersianCalendar();

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                FragmentStepOne.this,
                persianCalendar.getPersianYear(),
                persianCalendar.getPersianMonth(),
                persianCalendar.getPersianDay()
        );

        if (getActivity() != null)
            datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Toast.makeText(getContext(), year + "." + monthOfYear + "." + dayOfMonth, Toast.LENGTH_SHORT).show();

    }
}
