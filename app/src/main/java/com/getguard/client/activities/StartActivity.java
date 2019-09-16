package com.getguard.client.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.getguard.client.database.AppDatabase;
import com.getguard.client.database.User;

public class StartActivity extends AppCompatActivity {

    private static final String TAG = StartActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user = AppDatabase.getInstance(this).getUserDAO().getUser();

        if (user == null) {
            startActivity(new Intent(this, OnBoardingActivity.class));
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }

        finish();
    }
}
