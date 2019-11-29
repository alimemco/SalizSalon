package com.alirnp.salizsalon.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.alirnp.salizsalon.Model.Day;
import com.alirnp.salizsalon.NestedJson.Item;
import com.alirnp.salizsalon.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

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
        if (cm != null)
            return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
        else
            return false;
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

    public static String splitServices(String services) {
        StringBuilder sb = new StringBuilder();
        String[] split = services.split(",");

        for (String service : split) {
            sb.append(" *  ").append(service).append("\n");
        }
        return sb.toString();
    }

    public static String getStatus(Context context, Item item) {

        if (item.getStatus().equals(Constants.statusReserve.PENDING.getStatus())) {
            return context.getResources().getString(R.string.status_pending);

        } else if (item.getStatus().equals(Constants.statusReserve.DENIED.getStatus())) {
            return context.getResources().getString(R.string.status_denied);

        } else if (item.getStatus().equals(Constants.statusReserve.FINALIZED.getStatus())) {
            return context.getResources().getString(R.string.status_finalized);

        } else {
            return context.getResources().getString(R.string.status_done);

        }
    }


    public static int getStatusColor(Context context, Item item) {

        if (item.getStatus().equals(Constants.statusReserve.PENDING.getStatus())) {
            return ContextCompat.getColor(context, R.color.gray_blue_500);

        } else if (item.getStatus().equals(Constants.statusReserve.DENIED.getStatus())) {
            return ContextCompat.getColor(context, R.color.red_500);

        } else if (item.getStatus().equals(Constants.statusReserve.FINALIZED.getStatus())) {
            return ContextCompat.getColor(context, R.color.green_500);

        } else {
            return ContextCompat.getColor(context, R.color.blue_500);

        }
    }


    public static Drawable getDrawableFromStatus(Context context, Item item) {

        if (item.getStatus().equals(Constants.statusReserve.DENIED.getStatus())) {
            return ContextCompat.getDrawable(context, R.drawable.bg_circle_red);

        } else if (item.getStatus().equals(Constants.statusReserve.FINALIZED.getStatus())) {
            return ContextCompat.getDrawable(context, R.drawable.bg_circle_green);

        } else if (item.getStatus().equals(Constants.statusReserve.DONE.getStatus())) {
            return ContextCompat.getDrawable(context, R.drawable.bg_circle_blue);

        } else {
            return ContextCompat.getDrawable(context, R.drawable.bg_circle_gray);
        }
    }

    public static String getDayByNumber(Day day) {
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

    public static String convertDayNameToPersian(String day) {

        switch (day) {
            case "Sunday":
                return "یکشنبه";

            case "Monday":
                return "دوشنبه";

            case "Tuesday":
                return "سه شنبه";

            case "Wednesday":
                return "چهارشنبه";

            case "Thursday":
                return "پنجشنبه";

            case "Friday":
                return "جمعه";

            case "Saturday":
                return "شنبه";

            default:
                return "ParseError";
        }
    }




}
