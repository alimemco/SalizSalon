package com.alirnp.salizsalon.Views.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.Adapters.DaysAdapter;
import com.alirnp.salizsalon.Adapters.HoursAdapter;
import com.alirnp.salizsalon.Generator.DataGenerator;
import com.alirnp.salizsalon.Model.Day;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.NestedJson.ResponseJson;
import com.alirnp.salizsalon.NestedJson.Result;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ir.he.meowdatetimepicker.MeowTypefaceHelper;
import ir.he.meowdatetimepicker.date.DatePickerDialog;
import ir.he.meowdatetimepicker.utils.PersianCalendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentStepOne extends Fragment implements DatePickerDialog.OnDateSetListener
        , Callback<ResponseJson>
        , DaysAdapter.OnItemClickListener {

    private View view;

    private RecyclerView rcvDays;
    private RecyclerView rcvHours;

    private DaysAdapter daysAdapter;

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

        return view;
    }

    private void initRcv() {

        rcvDays = view.findViewById(R.id.fragment_step_one_rcv_days);
        rcvHours = view.findViewById(R.id.fragment_step_one_rcv_hours);

        rcvDays.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rcvHours.setLayoutManager(new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false));
        daysAdapter = new DaysAdapter(DataGenerator.getDays());
        daysAdapter.setOnItemClickListener(this);
        rcvDays.setAdapter(daysAdapter);


        getHours(daysAdapter.getModels().get(0));

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
    public void OnItemClick(Day day) {
        getHours(day);
    }

    private void getHours(Day day) {
        MyApplication.getApi().getTimes("times", getDayByNumber(day)).enqueue(this);
    }


    private String getDayByNumber(Day day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(day.getDate());
        int dayInt = cal.get(Calendar.DAY_OF_WEEK);

        switch (dayInt) {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";

        }
        return null;
    }

    @Override
    public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {


        if (response.body() != null) {
            Result result = response.body().getResult().get(0);
            if (result != null) {
                boolean success = Boolean.parseBoolean(response.body().getResult().get(0).getSuccess());

                if (success) {
                    Utils.log(FragmentStepOne.class, "item received " + response.body().getResult().get(0).getItems().size());

                    List<String> list = new ArrayList<>();

                    for (int i = 0; i < result.getItems().size(); i++) {
                        Utils.log(FragmentStepOne.class, result.getItems().get(i).getHour());
                        list.add(result.getItems().get(i).getHour());

                    }

                    HoursAdapter hoursAdapter = new HoursAdapter(list);

                    rcvHours.setAdapter(hoursAdapter);
                }
            }
        }
    }

    @Override
    public void onFailure(Call<ResponseJson> call, Throwable t) {

    }
}
