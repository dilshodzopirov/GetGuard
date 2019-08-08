package com.getguard.client.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.getguard.client.R;
import com.getguard.client.database.AppDatabase;
import com.getguard.client.database.User;
import com.getguard.client.database.UserDAO;
import com.getguard.client.fragments.HistoryFragment;
import com.getguard.client.fragments.MainFragment;
import com.getguard.client.fragments.OfertaFragment;
import com.getguard.client.fragments.ProfileFragment;
import com.getguard.client.fragments.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private LinearLayout headerContainer;
    private TextView titleText;

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

        getSupportActionBar().setTitle("");

        userDAO = AppDatabase.getInstance(this).getUserDAO();
        user = userDAO.getUser();

        nv = findViewById(R.id.nv);
        dl = findViewById(R.id.drawer);
        titleText = findViewById(R.id.title_text);

        View headerLayout = nv.getHeaderView(0);

        headerContainer = headerLayout.findViewById(R.id.header_container);

        t = new ActionBarDrawerToggle(this, dl, android.R.string.ok, android.R.string.no);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        nv.getMenu().getItem(0).setChecked(true);
        fragmentManager.beginTransaction().replace(R.id.content_layout, new MainFragment()).commit();

        nv.setNavigationItemSelectedListener(item -> {

            int id = item.getItemId();
            switch (id) {
                case R.id.main:
                    fragmentManager.beginTransaction().replace(R.id.content_layout, new MainFragment()).commit();
                    break;
                case R.id.history:
                    fragmentManager.beginTransaction().replace(R.id.content_layout, new HistoryFragment()).commit();
                    break;
                case R.id.oferta:
                    fragmentManager.beginTransaction().replace(R.id.content_layout, new OfertaFragment()).commit();
                    break;
                case R.id.settings:
                    fragmentManager.beginTransaction().replace(R.id.content_layout, new SettingsFragment()).commit();
                    break;
                case R.id.log_out:
                    logOut();
                    break;
                default:
                    return true;
            }

            dl.closeDrawer(GravityCompat.START);

            if (item.getItemId() == R.id.main) {
                getSupportActionBar().setTitle("");
                titleText.setVisibility(View.VISIBLE);
            } else {
                getSupportActionBar().setTitle(item.getTitle());
                titleText.setVisibility(View.GONE);
            }

            return true;

        });

        headerContainer.setOnClickListener(v -> {
            dl.closeDrawer(GravityCompat.START);
            fragmentManager.beginTransaction().replace(R.id.content_layout, new ProfileFragment()).commit();
            int size = nv.getMenu().size();
            for (int i = 0; i < size; i++) {
                nv.getMenu().getItem(i).setChecked(false);
            }
            getSupportActionBar().setTitle("Профиль");
            titleText.setVisibility(View.GONE);
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
