package com.alirnp.salizsalon.Views.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.Adapters.DaysAdapter;
import com.alirnp.salizsalon.Adapters.HoursAdapter;
import com.alirnp.salizsalon.Generator.DataGenerator;
import com.alirnp.salizsalon.Interface.OnDataReady;
import com.alirnp.salizsalon.Interface.OnStepReady;
import com.alirnp.salizsalon.Model.Day;
import com.alirnp.salizsalon.Model.Hour;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.NestedJson.ResponseJson;
import com.alirnp.salizsalon.NestedJson.Result;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import ir.he.meowdatetimepicker.MeowTypefaceHelper;
import ir.he.meowdatetimepicker.date.DatePickerDialog;
import ir.he.meowdatetimepicker.utils.PersianCalendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentStepOne extends Fragment implements DatePickerDialog.OnDateSetListener
        , Callback<ResponseJson>
        , DaysAdapter.OnItemClickListener,
        HoursAdapter.OnItemClickListener {

    private View view;


    private RecyclerView rcvDays;
    private RecyclerView rcvHours;

    private DaysAdapter daysAdapter;

    private HoursAdapter hoursAdapter;

    private OnStepReady onStepReady;
    private OnDataReady onDataReady;

    private HashMap<Constants.resultMap, String> result = new HashMap<>();

    public FragmentStepOne() {
    }

    public FragmentStepOne(OnStepReady onStepReady, OnDataReady onDataReady) {
        this.onStepReady = onStepReady;
        this.onDataReady = onDataReady;
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

        rcvHours.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false));

        daysAdapter = new DaysAdapter(DataGenerator.getDays());
        hoursAdapter = new HoursAdapter();

        daysAdapter.setOnItemClickListener(this);
        rcvDays.setAdapter(daysAdapter);


        getHours(daysAdapter.getModels().get(0));


    }


    private void initViews() {

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
    public void OnDayClick(Day day) {

        result.remove(Constants.resultMap.HOUR);
        getHours(day);

        validateStep(false);

        if (onDataReady != null) {
            onDataReady.onDayReceived(day);
            onDataReady.onHourReceived(null);
        }
    }

    @Override
    public void OnHourClick(Hour hour) {

        putToResult(hour);

        if (result.size() == Constants.resultMap.values().length)
            validateStep(true);

        if (onDataReady != null)
            onDataReady.onHourReceived(hour);


    }

    private void validateStep(boolean enable) {
        if (onStepReady != null)
            onStepReady.OnReady(1, enable);
    }

    private void getHours(Day day) {
        MyApplication.getApi().getTimes(Constants.TIMES, getDayByNumber(day)).enqueue(this);

        switchState(Constants.state.SEARCHING);

        putToResult(day);
    }

    private <T> void genericDisplay(T element) {
        System.out.println(element.getClass().getName() +
                " = " + element);
    }

    private <T> void putToResult(T data) {

        if (data instanceof Day) {
            Day day = (Day) data;

            result.put(Constants.resultMap.DAY_NAME, day.getDayName());
            result.put(Constants.resultMap.DAY_OF_MONTH, day.getDayOfMonth());
            result.put(Constants.resultMap.MONTH_NAME, day.getMonthName());
        } else if (data instanceof Hour) {
            Hour hour = (Hour) data;

            result.put(Constants.resultMap.HOUR, hour.getTime());
        }

    }


    private void switchState(Constants.state state) {
        switch (state) {
            case SEARCHING:
                rcvHours.setLayoutManager(getGridLayoutManager(1));
                hoursAdapter.setSearching();
                rcvHours.setAdapter(hoursAdapter);
                break;

            case ITEM_NOT_FOUND:
                rcvHours.setLayoutManager(getGridLayoutManager(1));
                hoursAdapter.setNotFound();
                break;

            case SUCCESS:
                rcvHours.setLayoutManager(getGridLayoutManager(3));
                break;
        }

    }

    private RecyclerView.LayoutManager getGridLayoutManager(int count) {
        return new GridLayoutManager(getContext(), count, RecyclerView.VERTICAL, false);
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
                boolean success = Boolean.parseBoolean(result.getSuccess());

                if (success) {
                    List<Hour> list = new ArrayList<>();

                    for (int i = 0; i < result.getItems().size(); i++) {
                        String time = result.getItems().get(i).getHour();
                        boolean open = Boolean.valueOf(result.getItems().get(i).getOpen());
                        list.add(new Hour(time, open));
                    }
                    switchState(Constants.state.SUCCESS);
                    hoursAdapter.setData(list, this);
                } else {
                    switchState(Constants.state.ITEM_NOT_FOUND);
                }
            }
        }
    }

    @Override
    public void onFailure(Call<ResponseJson> call, Throwable t) {
        switchState(Constants.state.ITEM_NOT_FOUND);
    }


}