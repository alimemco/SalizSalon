package com.alirnp.salizsalon.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.alirnp.salizsalon.Config;
import com.alirnp.salizsalon.Model.Day;
import com.alirnp.salizsalon.NestedJson.Item;
import com.alirnp.salizsalon.NestedJson.Result;
import com.alirnp.salizsalon.NestedJson.SalizResponse;
import com.alirnp.salizsalon.R;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class
Utils {


    private static final String TAG = "UtilsApp";

    public static void log(Class cls, String txt) {
        Log.i(TAG, cls.getName() + " | " + txt);
    }

    public static Callback<ResponseBody> callback = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            try {
                if (response.body() != null)
                    Utils.log(getClass(), response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            Utils.log(getClass(), t.getMessage());
        }
    };


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

    public static void sendMessageEditedTime(Context context) {
        Intent intent = new Intent(Constants.EVENT_EDITED_TIME);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void sendMessageReserved(Context context) {
        Intent intent = new Intent(Constants.EVENT_RESERVED);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void sendMessageAdminLevel(Context context) {
        Intent intent = new Intent(Constants.EVENT_ADMIN);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void sendMessageBannerChanged(Context context) {
        Intent intent = new Intent(Constants.EVENT_BANNER_CHANGED);
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
        return dp * Resources.getSystem().getDisplayMetrics().density;
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


    public static boolean isConnectedToThisServer(String host) {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 " + host);
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String numberToTextPrice(int price) {
        NumberFormat format = new DecimalFormat("#,###,###");
        return format.format(price) + " تومان ";
    }

    public static String numberToTextPrice(String price, boolean isStatic) {
        if (isInteger(price)) {
            int prc = Integer.valueOf(price);
            NumberFormat format = new DecimalFormat("#,###,###");

            if (isStatic)
                return format.format(prc) + " تومان ";
            else
                return "شروع از " + format.format(prc) + " تومان ";
        } else {
            return ".";
        }

    }

    public static boolean connected(Context context) {
        if (context != null) {

            if (Utils.isConnected(context)) {
                return Utils.isConnectedToThisServer(Config.BASE_URL);
            }

        }
        return false;
    }


    public static void sendSmsToAdmin(String messageToAdmin) {
        Map<String, String> map = new HashMap<>();
        map.put(Constants.MESSAGE_ADMIN, messageToAdmin);

        MyApplication.getApi().sendSms(Constants.TOKEN, map).enqueue(callback);
    }

    public static void sendSmsToClient(String messageClient) {
        Map<String, String> map = new HashMap<>();
        String receptorClient = MyApplication.getSharedPrefManager().getUser().getPhone();

        if (receptorClient != null) {
            map.put(Constants.RECEPTOR_CLIENT, receptorClient);
            map.put(Constants.MESSAGE_CLIENT, messageClient);

            MyApplication.getApi().sendSms(Constants.TOKEN, map).enqueue(callback);
        }
    }

    public static boolean validateCallbackSalizResponse(Context context, Response<SalizResponse> response) {
        String error;

        if (response.isSuccessful()) {
            if (response.body() != null) {
                if (response.code() == 200) {
                    Result result = response.body().getResult().get(0);
                    boolean success = Boolean.parseBoolean(result.getSuccess());
                    if (success) {
                        if (result.getItems() != null) {
                            return true;
                        } else {
                            error = context.getString(R.string.error_emptyItems);
                        }

                    } else {
                        error = result.getMessage();
                    }
                } else {
                    error = context.getString(R.string.error_emptyHttpOK);
                }

            } else {
                error = context.getString(R.string.error_unSuccess);
            }
        } else {
            error = context.getString(R.string.error_empty_body);
        }
        Toast.makeText(context, error, Toast.LENGTH_LONG).show();
        return false;
    }

}
