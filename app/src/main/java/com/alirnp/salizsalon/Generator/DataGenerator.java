package com.alirnp.salizsalon.Generator;

import com.alirnp.salizsalon.Model.Day;
import com.alirnp.salizsalon.Model.Item;
import com.alirnp.salizsalon.Model.Service;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class DataGenerator {

    public static List<Item> getHomeItems() {

        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item(0, "گرفتن نوبت", R.drawable.ic_clock));
/*
        for (int i = 0; i < 2; i++) {
            switch (i) {

                case 0:
                    itemList.add(new Item(i, "گرفتن نوبت", R.drawable.ic_clock));
                    break;


                case 1:
                    itemList.add(new Item(i, "فروشگاه سالیز", R.drawable.ic_shop));
                    break;


            }
        }*/

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

    public static ArrayList<Service> getServices() {

        ArrayList<Service> list = new ArrayList<>();
        ArrayList<String> listTitle = new ArrayList<>();

        listTitle.add("اصلاح صورت");
        listTitle.add("شنیون");
        listTitle.add("کوتاهی ساده");
        listTitle.add("اصلاح ناخن");
        listTitle.add("میکاپ ساده");
        listTitle.add("میکاپ حرفه ای");
        listTitle.add("سشوار");
        listTitle.add("اپیلاسیون");
        listTitle.add("پدیکور");
        listTitle.add("مانی کور");

        for (int i = 0; i < listTitle.size(); i++) {
            Service service = new Service();
            service.setName(listTitle.get(i));
            service.setPrice((randomDigit(1, 15)) * 10000);
            list.add(service);
        }

        return list;
    }

    public static List<Day> getWeekDays() {

        List<Day> items = new ArrayList<>();
        Day day = new Day();

        for (int i = 1; i <= 7; i++) {

            switch (i) {
                case 1:
                    day = new Day(Constants.Saturday, i);
                    break;

                case 2:
                    day = new Day(Constants.Sunday, i);
                    break;


                case 3:
                    day = new Day(Constants.Monday, i);
                    break;


                case 4:
                    day = new Day(Constants.Tuesday, i);
                    break;


                case 5:
                    day = new Day(Constants.Wednesday, i);
                    break;

                case 6:
                    day = new Day(Constants.Thursday, i);
                    break;

                case 7:
                    day = new Day(Constants.Friday, i);
                    break;
            }

            items.add(day);
        }

        return items;

    }

    public static int randomDigit(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
