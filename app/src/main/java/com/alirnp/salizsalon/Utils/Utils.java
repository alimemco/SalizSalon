package com.alirnp.salizsalon.Utils;

import android.util.Log;

public class Utils {

    private static final String TAG = "UtilsApp";

    public static void log(Class cls , String txt){
        Log.i(TAG, cls.getName()+" | "+txt);
    }

}
