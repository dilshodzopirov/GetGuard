package com.getguard.client.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.getguard.client.R;

public class OfferActivity extends AppCompatActivity {

    private AppCompatEditText amountInput;
    private LinearLayout confirmLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Укажите стоимость");

        amountInput = findViewById(R.id.amount_input);
        confirmLayout = findViewById(R.id.confirm_layout);

        confirmLayout.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("amount", amountInput.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else {

        }
        return super.onOptionsItemSelected(item);
    }

}
