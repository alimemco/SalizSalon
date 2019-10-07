package com.alirnp.salizsalon.Views.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.Adapters.DaysAdapter;
import com.alirnp.salizsalon.Generator.DataGenerator;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.NestedJson.ResponseJson;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ir.he.meowdatetimepicker.MeowTypefaceHelper;
import ir.he.meowdatetimepicker.date.DatePickerDialog;
import ir.he.meowdatetimepicker.utils.PersianCalendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;


public class FragmentStepOne extends Fragment implements DatePickerDialog.OnDateSetListener
        , Callback<ResponseJson> {

    private View view;

    private RecyclerView rcv;

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

        initRcv();

        getOpenTimes();


        PersianDate pDate = new PersianDate();

        PersianDateFormat pFormatter = new PersianDateFormat("Y/m/d");

        Utils.log(FragmentStepOne.class, pFormatter.format(pDate));

        pDate.setShDay(pDate.getShDay() + 2);

        Utils.log(FragmentStepOne.class, pFormatter.format(pDate));


        return view;
    }

    private void initRcv() {

        rcv = view.findViewById(R.id.fragment_step_one_rcv);
        rcv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rcv.setAdapter(new DaysAdapter(DataGenerator.getDays()));
    }

    private void getOpenTimes() {


        MyApplication.getApi().getTimes("times", getCurrentDay()).enqueue(this);


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

    @Override
    public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {

        if (response.body() != null) {
            Utils.log(FragmentStepOne.class, String.valueOf(response.body().getResult().get(0).getItems().size()));
        }

    }

    @Override
    public void onFailure(Call<ResponseJson> call, Throwable t) {

    }
}
