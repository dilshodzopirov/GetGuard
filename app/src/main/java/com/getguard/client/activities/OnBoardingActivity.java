package com.getguard.client.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.getguard.client.R;
import com.getguard.client.adapters.OnBoardingViewPagerAdapter;
import com.rd.PageIndicatorView;

public class OnBoardingActivity extends AppCompatActivity {

    private ViewPager viewPager;

    private OnBoardingViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        viewPager = findViewById(R.id.view_pager);

        adapter = new OnBoardingViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        PageIndicatorView pageIndicatorView = findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {/*empty*/}

            @Override
            public void onPageSelected(int position) {
                if (position == 5) {
                    startActivity(new Intent(OnBoardingActivity.this, LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {/*empty*/}
        });

    }
}
