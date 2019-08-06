package com.getguard.client.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.getguard.client.R;
import com.getguard.client.adapters.TabAdapter;
import com.getguard.client.database.AppDatabase;
import com.getguard.client.database.User;
import com.getguard.client.database.UserDAO;
import com.getguard.client.fragments.EventsFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LinearLayout createLayout;

    private UserDAO userDAO;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("GET GUARD");

        userDAO = AppDatabase.getInstance(this).getUserDAO();
        user = userDAO.getUser();

        dl = findViewById(R.id.drawer);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        createLayout = findViewById(R.id.create_layout);

        t = new ActionBarDrawerToggle(this, dl, android.R.string.ok, android.R.string.no);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.main:
                    Toast.makeText(MainActivity.this, "My Account", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.history:
                    Toast.makeText(MainActivity.this, "My Cart", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.oferta:
                    Toast.makeText(MainActivity.this, "My Cart", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.settings:
                    Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.log_out:
                    logOut();
                    break;
                default:
                    return true;
            }


            return true;

        });

        viewPager.setOffscreenPageLimit(3);

        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(EventsFragment.newInstance(0), "Все");
        adapter.addFragment(EventsFragment.newInstance(1), "Мои заявки");
        adapter.addFragment(EventsFragment.newInstance(2), "Активные");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        createLayout.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SelectEventTypeActivity.class));
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    private void logOut() {
        new AlertDialog.Builder(this)
                .setTitle("Выйти")
                .setMessage("Вы действительно хотите выйти?")
                .setPositiveButton("Да", (dialog, which) -> {
                    userDAO.delete(user);
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

}
