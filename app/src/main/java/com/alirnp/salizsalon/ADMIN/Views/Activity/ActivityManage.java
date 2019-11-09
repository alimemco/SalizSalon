package com.alirnp.salizsalon.ADMIN.Views.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.alirnp.salizsalon.ADMIN.Adapter.TabAdapter;
import com.alirnp.salizsalon.ADMIN.Views.Fragment.FragmentManageOrder;
import com.alirnp.salizsalon.ADMIN.Views.Fragment.FragmentManageTimes;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Utils;
import com.google.android.material.tabs.TabLayout;

public class ActivityManage extends AppCompatActivity {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        initViews();
        initViewPager();
        setCustomFont();

    }

    private void initViewPager() {
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentManageOrder(), "سفارشات");
        adapter.addFragment(new FragmentManageTimes(), "زمان بندی");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initViews() {
        viewPager = findViewById(R.id.activity_manage_viewPager);
        tabLayout = findViewById(R.id.activity_manage_tabLayout);
    }

    public void setCustomFont() {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();

        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);

            int tabChildsCount = vgTab.getChildCount();

            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(MyApplication.getIranSans(ActivityManage.this));
                    ((TextView) tabViewChild).setTextSize(Utils.dpToPxFloat(getResources().getDimension(R.dimen.large_4X_sp)));
                }
            }
        }
    }
}
