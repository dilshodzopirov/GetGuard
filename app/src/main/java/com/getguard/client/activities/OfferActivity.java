package com.getguard.client.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.getguard.client.R;

public class OfferActivity extends AppCompatActivity {

    private AppCompatEditText amountInput;
    private LinearLayout confirmLayout;
    private TextView hoursText, amountText;

    private int hours = 0;
    private String totalAmount = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Укажите стоимость");
        hours = getIntent().getIntExtra("hours", 0);

        amountInput = findViewById(R.id.amount_input);
        confirmLayout = findViewById(R.id.confirm_layout);
        hoursText = findViewById(R.id.hours_text);
        amountText = findViewById(R.id.amount_text);

        hoursText.setText(String.valueOf(hours));
        amountText.setText("0\u20BD");

        amountInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int amount = 0;
                try {
                    amount = Integer.parseInt(editable.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                totalAmount = String.valueOf(amount * hours);
                amountText.setText(totalAmount + "\u20BD");
            }
        });

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
