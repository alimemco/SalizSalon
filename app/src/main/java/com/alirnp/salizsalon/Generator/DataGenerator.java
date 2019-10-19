package com.alirnp.salizsalon.Generator;

import com.alirnp.salizsalon.Model.Day;
import com.alirnp.salizsalon.Model.Hour;
import com.alirnp.salizsalon.Model.Item;
import com.alirnp.salizsalon.R;

import java.util.ArrayList;
import java.util.List;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class DataGenerator {

    public static List<Item> getHomeItems() {

        List<Item> itemList = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            switch (i) {

                case 0:
                    itemList.add(new Item(i, "گرفتن نوبت", R.drawable.ic_clock));
                    break;


                case 1:
                    itemList.add(new Item(i, "خدمات سالیز", R.drawable.ic_shop));
                    break;


            }
        }

        return itemList;
    }


    public static ArrayList<Day> getDays() {

        ArrayList<Day> list = new ArrayList<>();
        PersianDate pDate = new PersianDate();


        PersianDateFormat pFormatterDay = new PersianDateFormat("j");
        PersianDateFormat pFormatterDayName = new PersianDateFormat("l");
        PersianDateFormat pFormatterMonthName = new PersianDateFormat("F");

        for (int i = 0; i < 7; i++) {

            list.add(new Day(pFormatterDayName.format(pDate),
                    pFormatterMonthName.format(pDate),
                    pFormatterDay.format(pDate), pDate.toDate()));

            pDate.setShDay(pDate.getShDay() + 1);

        }

        return list;
    }


}
