package com.alirnp.salizsalon.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.alirnp.salizsalon.Dialog.LoadingDialog;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class
Utils {



    private static final String TAG = "UtilsApp";

    public static void log(Class cls, String txt) {
        Log.i(TAG, cls.getName() + " | " + txt);
    }

    public static String numberToTextPrice(int price) {
        NumberFormat format = new DecimalFormat("#,###,###");
        return format.format(price) + " تومان ";
    }

    public static String numberToTextPrice(String price) {
        if (isInteger(price)) {
            int prc = Integer.valueOf(price);
            NumberFormat format = new DecimalFormat("#,###,###");
            return format.format(prc) + " تومان ";
        } else {
            return ".";
        }

    }

    public static String parseUserLevel(String user_level) {

        if (user_level == null) {
            return Constants.NEW_COMER_PER;

        } else {
            if (user_level.equals(Constants.user_level.NORMAL.getLevel())) {
                return Constants.NORMAL_PER;

            } else if (user_level.equals(Constants.user_level.GOLD.getLevel())) {
                return Constants.GOLD_PER;

            } else if (user_level.equals(Constants.user_level.ADMIN.getLevel())) {
                return Constants.ADMIN_PER;

            } else {
                return Constants.NEW_COMER_PER;
            }
        }


    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public static void sendMessageLogin(Context context) {
        Intent intent = new Intent(Constants.EVENT_LOGIN);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void sendMessageReserved(Context context) {
        Intent intent = new Intent(Constants.EVENT_RESERVED);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }


    public static boolean isInteger(String number) {
        try {
            Integer.parseInt(number);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static float dpToPxFloat(float dp) {
        return (float) (dp * Resources.getSystem().getDisplayMetrics().density);
    }


}
