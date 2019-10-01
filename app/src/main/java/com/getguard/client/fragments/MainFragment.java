package com.getguard.client.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.getguard.client.R;
import com.getguard.client.activities.SelectEventTypeActivity;
import com.getguard.client.adapters.TabAdapter;
import com.getguard.client.database.AppDatabase;
import com.getguard.client.database.User;
import com.google.android.material.tabs.TabLayout;

public class MainFragment extends Fragment {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LinearLayout createLayout;

    private User user;
    private EventsFragment allFragment;
    private EventsFragment myFragment;
    private EventsFragment activeFragment;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        user = AppDatabase.getInstance(getActivity()).getUserDAO().getUser();
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        createLayout = view.findViewById(R.id.create_layout);

        allFragment = EventsFragment.newInstance(0);
        myFragment = EventsFragment.newInstance(1);
        activeFragment = EventsFragment.newInstance(2);

        viewPager.setOffscreenPageLimit(3);

        adapter = new TabAdapter(getChildFragmentManager());
        adapter.addFragment(allFragment, "Все");
        adapter.addFragment(myFragment, "Мои заявки");
        adapter.addFragment(activeFragment, "Активные");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        if (user.getRoleType() != 1) {
            createLayout.setVisibility(View.GONE);
        } else {
            createLayout.setOnClickListener(v -> {
                this.startActivityForResult(new Intent(getActivity(), SelectEventTypeActivity.class), 6666);
            });
        }

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == 6666) {
            if (allFragment != null) {
                allFragment.getEventTypes();
            }
            if (myFragment != null) {
                myFragment.getEventTypes();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
