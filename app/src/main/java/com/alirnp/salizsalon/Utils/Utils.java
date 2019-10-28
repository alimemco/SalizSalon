package com.alirnp.salizsalon.Utils;

import android.util.Log;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Utils {

    private static final String TAG = "UtilsApp";

    public static void log(Class cls , String txt){
        Log.i(TAG, cls.getName()+" | "+txt);
    }

    public static String numberToTextPrice(int price) {
        NumberFormat format = new DecimalFormat("#,###,###");
        return format.format(price) + " تومان ";
    }

}
