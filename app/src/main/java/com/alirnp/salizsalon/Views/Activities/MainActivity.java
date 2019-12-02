package com.alirnp.salizsalon.Views.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.alirnp.salizsalon.ADMIN.Views.Activity.ActivityManage;
import com.alirnp.salizsalon.Dialog.LoadingDialog;
import com.alirnp.salizsalon.Interface.OnLogoutUser;
import com.alirnp.salizsalon.Model.User;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.NestedJson.ResponseJson;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;
import com.alirnp.salizsalon.Views.Fragments.FragmentHome;
import com.alirnp.salizsalon.Views.Fragments.FragmentOrder;
import com.alirnp.salizsalon.Views.Fragments.FragmentUser;
import com.alirnp.salizsalon.Views.Fragments.FragmentUserInfo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.BaselineLayout;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener,
        OnLogoutUser {

    FragmentManager fragmentManager;
    boolean doubleBackToExitPressedOnce = false;
    Constants.fragmentToShow fragmentToShow;
    private BottomNavigationView bottomNavigationView;
    private ImageView kingImg;

    private LoadingDialog dialog;

    private FragmentHome fragmentHome = FragmentHome.newInstance();
    private FragmentOrder fragmentOrder = FragmentOrder.newInstance();
    private FragmentUserInfo fragmentUserInfo = new FragmentUserInfo(this);
    private FragmentUser fragmentUser = new FragmentUser();
    private BroadcastReceiver mLoginReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            fragmentToShow = Constants.fragmentToShow.HOME;

            showAdminManager();
        }
    };

    private void showAdminManager() {
        boolean visible;
        String userLevel = MyApplication.getSharedPrefManager().getUser().getLevel();
        visible = userLevel.equals(Constants.user_level.ADMIN.getLevel());
        kingImg.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private BroadcastReceiver mBroadcastReserved = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            fragmentToShow = Constants.fragmentToShow.ORDER;
        }
    };

    private void initViews() {

        bottomNavigationView = findViewById(R.id.activity_main_bottomNav);
        kingImg = findViewById(R.id.activity_main_toolbar_king);
        kingImg.setOnClickListener(this);

        showAdminManager();

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (fragmentToShow == null)
            fragmentToShow = Constants.fragmentToShow.HOME;


        switch (fragmentToShow) {
            case HOME:
                bottomNavigationView.setSelectedItemId(R.id.home);
                break;

            case USER:
                bottomNavigationView.setSelectedItemId(R.id.user);
                break;

            case ORDER:
                bottomNavigationView.setSelectedItemId(R.id.order);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        setNavigationTypeface();

        initBroadcasts();

    }


    public void setNavigationTypeface() {
        ViewGroup navigationGroup = (ViewGroup) bottomNavigationView.getChildAt(0);
        for (int i = 0; i < navigationGroup.getChildCount(); i++) {
            ViewGroup navUnit = (ViewGroup) navigationGroup.getChildAt(i);
            for (int j = 0; j < navUnit.getChildCount(); j++) {
                View navUnitChild = navUnit.getChildAt(j);
                if (navUnitChild instanceof BaselineLayout) {
                    BaselineLayout baselineLayout = (BaselineLayout) navUnitChild;
                    for (int k = 0; k < baselineLayout.getChildCount(); k++) {
                        View baselineChild = baselineLayout.getChildAt(k);
                        if (baselineChild instanceof TextView) {
                            TextView textView = (TextView) baselineChild;
                            textView.setTypeface(MyApplication.getIranSans(MainActivity.this));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount == 0) {
            goBackWithTimer();
        } else {
            super.onBackPressed();
        }
    }

    private void goBackWithTimer() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        DetectFragment();

        this.doubleBackToExitPressedOnce = true;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private void DetectFragment() {
        Fragment frCurrent = getSupportFragmentManager().findFragmentById(R.id.activity_main_fragment);
        if (!(frCurrent instanceof FragmentHome)) {
            replace(fragmentHome);
        }
    }

    private void clearFragmentBackStack() {
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStack();
        }
    }

    @Override
    public void onLogout() {
        MyApplication.getSharedPrefManager().saveUser(null);
        bottomNavigationView.setSelectedItemId(R.id.home);
        showAdminManager();
    }

    private void initBroadcasts() {
        LocalBroadcastManager.getInstance(this).registerReceiver(mLoginReceiver,
                new IntentFilter(Constants.EVENT_LOGIN));

        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReserved,
                new IntentFilter(Constants.EVENT_RESERVED));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.home:
                replace(fragmentHome);
                return true;

            case R.id.user:
                User user = MyApplication.getSharedPrefManager().getUser();

                String firstName = user.getFirstName();
                if (firstName != null)
                    replace(fragmentUserInfo);
                else
                    replace(fragmentUser);


                return true;

            case R.id.order:
                replace(fragmentOrder);
                return true;

        }

        return false;
    }

    private void replace(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_main_fragment, fragment);
        fragmentTransaction.commit();

        if (fragment instanceof FragmentHome) {
            fragmentToShow = Constants.fragmentToShow.HOME;
        } else if (fragment instanceof FragmentUser || fragment instanceof FragmentUserInfo) {
            fragmentToShow = Constants.fragmentToShow.USER;
        } else if (fragment instanceof FragmentOrder) {
            fragmentToShow = Constants.fragmentToShow.ORDER;
        }


    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.activity_main_toolbar_king) {
            User user = MyApplication.getSharedPrefManager().getUser();
            Map<String, String> info = new HashMap<>();
            info.put(Constants.PHONE, user.getPhone());
            MyApplication.getApi().userManager(Constants.CHECK_ADMIN, info).enqueue(callback());
            showLoading();
        }
    }

    private Callback<ResponseJson> callback() {

        return new Callback<ResponseJson>() {
            @Override
            public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                startActivity(new Intent(MainActivity.this, ActivityManage.class));
                dismissLoading();
            }

            @Override
            public void onFailure(Call<ResponseJson> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                dismissLoading();
            }
        };
    }

    private void showLoading() {
        FragmentManager fm = getSupportFragmentManager();
        dialog = new LoadingDialog();
        dialog.show(fm, "LoadingDialog");
    }

    private void dismissLoading() {
        if (dialog !=null)
        dialog.dismiss();
    }


}
