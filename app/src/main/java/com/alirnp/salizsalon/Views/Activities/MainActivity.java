package com.alirnp.salizsalon.Views.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Views.Fragments.FragmentHome;
import com.alirnp.salizsalon.Views.Fragments.FragmentUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.BaselineLayout;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {

    FragmentManager fragmentManager;
    boolean doubleBackToExitPressedOnce = false;
    private BottomNavigationView btmView;
    private FragmentHome fragmentHome = FragmentHome.newInstance();
    private FragmentUser fragmentUser = FragmentUser.newInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        //setFontBottomNavigation(btmView);
        setNavigationTypeface();

        replace(fragmentHome);


    }

    private void initViews() {

        btmView = findViewById(R.id.activity_main_bottomNav);

        btmView.setOnNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.home:
                replace(fragmentHome);
                return true;

            case R.id.account:
                replace(fragmentUser);
                return true;

            case R.id.setting:
                // replace(fragmentHome);
                return true;

        }

        return false;
    }

    public void add(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_main_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void delete() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        }
        fragmentTransaction.commit();
    }

    private void replace(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_main_fragment, fragment);
        fragmentTransaction.commit();
    }

    public void setNavigationTypeface() {
        ViewGroup navigationGroup = (ViewGroup) btmView.getChildAt(0);
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

}
