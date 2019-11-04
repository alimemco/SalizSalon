package com.alirnp.salizsalon.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.alirnp.salizsalon.Model.User;

public class SharedPrefManager {

    SharedPreferences sharedPreferences;


    public SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveUser(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.FIRST_NAME, user.getFirstName());
        editor.putString(Constants.LAST_NAME, user.getLastName());
        editor.putString(Constants.PHONE, user.getPhone());
        editor.apply();
    }

    public User getUser() {
        User user = new User();
        user.setFirstName(sharedPreferences.getString(Constants.FIRST_NAME, null));
        user.setFirstName(sharedPreferences.getString(Constants.LAST_NAME, null));
        user.setFirstName(sharedPreferences.getString(Constants.PHONE, null));
        return user;
    }
}
